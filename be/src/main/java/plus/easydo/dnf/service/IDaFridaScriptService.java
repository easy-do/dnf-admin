package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.entity.DaFridaScript;
import plus.easydo.dnf.qo.FridaScriptQo;

/**
 * frida脚本信息 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaFridaScriptService extends IService<DaFridaScript> {

    Page<DaFridaScript> pageFridaScript(FridaScriptQo fridaScriptQo);

    boolean saveFridaScript(DaFridaScript daFridaScript);
}
