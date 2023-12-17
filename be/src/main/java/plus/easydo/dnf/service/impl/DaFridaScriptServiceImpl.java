package plus.easydo.dnf.service.impl;


import cn.hutool.core.text.CharSequenceUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.constant.SystemConstant;
import plus.easydo.dnf.entity.DaFridaFunction;
import plus.easydo.dnf.entity.DaFridaScript;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.mapper.DaFridaScriptMapper;
import plus.easydo.dnf.qo.FridaScriptQo;
import plus.easydo.dnf.service.IDaFridaFunctionService;
import plus.easydo.dnf.service.IDaFridaScriptService;

import java.util.LinkedHashMap;
import java.util.Map;

import static plus.easydo.dnf.entity.table.DaFridaScriptTableDef.DA_FRIDA_SCRIPT;

/**
 * frida脚本信息 服务层实现。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class DaFridaScriptServiceImpl extends ServiceImpl<DaFridaScriptMapper, DaFridaScript> implements IDaFridaScriptService {

    private final IDaFridaFunctionService iDaFridaFunctionService;


    @Override
    public Page<DaFridaScript> pageFridaScript(FridaScriptQo fridaScriptQo) {
        QueryWrapper query = query()
                .and(DA_FRIDA_SCRIPT.SCRIPT_NAME.like(fridaScriptQo.getScriptName()))
                .and(DA_FRIDA_SCRIPT.REMARK.like(fridaScriptQo.getRemark()));
        return page(new Page<>(fridaScriptQo.getCurrent(), fridaScriptQo.getPageSize()), query);
    }

    @Override
    public boolean saveFridaScript(DaFridaScript daFridaScript) {
        String childrenFunction = daFridaScript.getChildrenFunction();
        if (CharSequenceUtil.isNotBlank(childrenFunction)) {
            StringBuilder stringBuilder = new StringBuilder();
            //开始拼接
            DaGameConfig beginConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.INTRODUCE_FUNCTIONS_BEGIN);
            DaGameConfig endConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.INTRODUCE_FUNCTIONS_END);
            DaGameConfig funBeginConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FUNCTION_CONTEXT_BEGIN);
            DaGameConfig funEndConfig = CacheManager.GAME_CONF_MAP.get(SystemConstant.FUNCTION_CONTEXT_END);
            stringBuilder.append(beginConfig.getConfData());
            stringBuilder.append(" \r\n ");
            Map<String, DaFridaFunction> allChildrenFunction = iDaFridaFunctionService.getChildrenFunction(new LinkedHashMap<>(),childrenFunction);
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
            stringBuilder.append(" \r\n ");
            daFridaScript.setScriptContext(stringBuilder.toString());
        }
        return save(daFridaScript);
    }
}
