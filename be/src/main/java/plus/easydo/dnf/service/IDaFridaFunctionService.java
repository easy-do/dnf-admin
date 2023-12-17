package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaFridaFunction;
import plus.easydo.dnf.qo.FridaFunctionQo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * frida函数信息 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaFridaFunctionService extends IService<DaFridaFunction> {

    Page<DaFridaFunction> pageFridaFunction(FridaFunctionQo fridaFunctionQo);

    List<DaFridaFunction> listFridaFunction();

    boolean saveFridaFunction(DaFridaFunction daFridaFunction);

    boolean updateFridaFunction(DaFridaFunction daFridaFunction);

    DaFridaFunction getInfo(Serializable id);

    DaFridaFunction getByKey(String key);

    Map<String, DaFridaFunction> getChildrenFunction(Map<String,DaFridaFunction> objectObjectHashMap, String childrenFunction);
    List<DaFridaFunction> getChildrenFunction(Long id);
}
