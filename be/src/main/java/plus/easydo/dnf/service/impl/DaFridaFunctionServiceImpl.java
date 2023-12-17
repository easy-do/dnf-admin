package plus.easydo.dnf.service.impl;


import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.entity.DaFridaFunction;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.mapper.DaFridaFunctionMapper;
import plus.easydo.dnf.qo.FridaFunctionQo;
import plus.easydo.dnf.service.IDaFridaFunctionService;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static plus.easydo.dnf.entity.table.DaFridaFunctionTableDef.DA_FRIDA_FUNCTION;

/**
 * frida函数信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
public class DaFridaFunctionServiceImpl extends ServiceImpl<DaFridaFunctionMapper, DaFridaFunction> implements IDaFridaFunctionService {

    @Override
    public Page<DaFridaFunction> pageFridaFunction(FridaFunctionQo fridaFunctionQo) {
        QueryWrapper query = query()
                .select(DA_FRIDA_FUNCTION.ID, DA_FRIDA_FUNCTION.FUNCTION_NAME, DA_FRIDA_FUNCTION.REMARK, DA_FRIDA_FUNCTION.FUNCTION_KEY, DA_FRIDA_FUNCTION.IS_SYSTEM_FUN)
                .and(DA_FRIDA_FUNCTION.FUNCTION_NAME.like(fridaFunctionQo.getFunctionName()))
                .and(DA_FRIDA_FUNCTION.FUNCTION_KEY.like(fridaFunctionQo.getFunctionKey()))
                .and(DA_FRIDA_FUNCTION.REMARK.like(fridaFunctionQo.getRemark()))
                .and(DA_FRIDA_FUNCTION.IS_SYSTEM_FUN.eq(fridaFunctionQo.getIsSystemFun()));
        return page(new Page<>(fridaFunctionQo.getCurrent(),fridaFunctionQo.getPageSize()),query);
    }

    @Override
    public List<DaFridaFunction> listFridaFunction() {
        QueryWrapper query = query().select(DA_FRIDA_FUNCTION.ID, DA_FRIDA_FUNCTION.FUNCTION_KEY,DA_FRIDA_FUNCTION.FUNCTION_NAME);
        return list(query);
    }

    @Override
    public boolean saveFridaFunction(DaFridaFunction daFridaFunction) {
        removeIntroduceFun(daFridaFunction);
        return save(daFridaFunction);
    }

    private void checkSystemFun(Long id) {
        DaFridaFunction daFridaFunction = getById(id);
        if(Objects.isNull(daFridaFunction)){
            throw new BaseException("函数不存在");
        }
        if(daFridaFunction.getIsSystemFun()){
            throw new BaseException("不允许修改系统函数");
        }
    }

    @Override
    public boolean updateFridaFunction(DaFridaFunction daFridaFunction) {
        checkSystemFun(daFridaFunction.getId());
        removeIntroduceFun(daFridaFunction);
        return updateById(daFridaFunction);
    }

    @Override
    public DaFridaFunction getInfo(Serializable id) {
        DaFridaFunction daFridaFunction = getById(id);
        if(Objects.nonNull(daFridaFunction) && CharSequenceUtil.isNotBlank(daFridaFunction.getChildrenFunction())){
            String childrenFunction = daFridaFunction.getChildrenFunction();
            StringBuilder stringBuilder = new StringBuilder();
            //开始拼接
            DaGameConfig beginConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.TEMPORARY_INTRODUCE_FUNCTIONS_BEGIN);
            DaGameConfig endConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.TEMPORARY_INTRODUCE_FUNCTIONS_END);
            DaGameConfig funBeginConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FUNCTION_CONTEXT_BEGIN);
            DaGameConfig funEndConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FUNCTION_CONTEXT_END);
            stringBuilder.append(beginConfig.getConfData());
            stringBuilder.append(" \r\n ");
            Map<String, DaFridaFunction> allChildrenFunction = getChildrenFunction(MapUtil.newHashMap(),childrenFunction);
            for (Map.Entry<String,DaFridaFunction> fun : allChildrenFunction.entrySet()) {
                DaFridaFunction fridaFunction = fun.getValue();
                if (CharSequenceUtil.isNotBlank(fridaFunction.getFunctionContext())) {
                    stringBuilder.append(CharSequenceUtil.format(funBeginConfig.getConfData(),fridaFunction.getFunctionName(),fridaFunction.getRemark()));
                    stringBuilder.append(" \r\n ");
                    stringBuilder.append(fridaFunction.getFunctionContext());
                    stringBuilder.append(" \r\n ");
                    stringBuilder.append(CharSequenceUtil.format(funEndConfig.getConfData(),fridaFunction.getFunctionName(),fridaFunction.getRemark()));
                    stringBuilder.append(" \r\n ");
                }
            }
            stringBuilder.append(" \r\n ");
            stringBuilder.append(endConfig.getConfData());
            stringBuilder.append(CharSequenceUtil.isBlank(daFridaFunction.getFunctionContext())?"":daFridaFunction.getFunctionContext());
            daFridaFunction.setFunctionContext(stringBuilder.toString());
        }
        return daFridaFunction;
    }

    /**
     * 删除引入的函数
     *
     * @param daFridaFunction daFridaFunction
     * @author laoyu
     * @date 2023/12/10
     */
    private void removeIntroduceFun(DaFridaFunction daFridaFunction) {
        String context = daFridaFunction.getFunctionContext();
        if(CharSequenceUtil.isNotBlank(context)){
            DaGameConfig config = CacheManager.GAME_CONF_MAP.get(SystemConstant.TEMPORARY_INTRODUCE_FUNCTIONS_END);
            String introduceBefore = CharSequenceUtil.subBefore(context, config.getConfData(), true);
            if(!introduceBefore.equals(context)){
                context = CharSequenceUtil.subAfter(context,config.getConfData(),true);
                daFridaFunction.setFunctionContext(context);
            }
        }
    }

    @Override
    public DaFridaFunction getByKey(String key){
        QueryWrapper query = query().and(DA_FRIDA_FUNCTION.FUNCTION_KEY.eq(key));
        return getOne(query);
    }

    @Override
    public Map<String,DaFridaFunction> getChildrenFunction( Map<String,DaFridaFunction> allFunction,String childrenFunction){
        List<String> functions = CharSequenceUtil.split(childrenFunction, ",");
        for (String fun : functions) {
            DaFridaFunction fridaFunction = getByKey(fun);
            if(Objects.nonNull(fridaFunction) && !allFunction.containsKey(fridaFunction.getFunctionKey())){
                allFunction.put(fridaFunction.getFunctionKey(),fridaFunction);
                if(CharSequenceUtil.isNotBlank(fridaFunction.getChildrenFunction())){
                    getChildrenFunction(allFunction,fridaFunction.getChildrenFunction());
                }
            }
        }
        return allFunction;
    }

    @Override
    public List<DaFridaFunction> getChildrenFunction(Long id) {
        DaFridaFunction daFridaFunction = getById(id);
        if(Objects.isNull(daFridaFunction)){
            throw new BaseException("函数不存在");
        }
        String childrenFunction = daFridaFunction.getChildrenFunction();
        if(CharSequenceUtil.isBlank(childrenFunction)){
            return ListUtil.empty();
        }
        List<String> funKeys = CharSequenceUtil.split(childrenFunction, ",");
        return getByKeys(funKeys);
    }

    private List<DaFridaFunction> getByKeys(List<String> funKeys) {
        QueryWrapper query = query().and(DA_FRIDA_FUNCTION.FUNCTION_KEY.in(funKeys));
        return list(query);
    }
}
