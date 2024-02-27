package plus.easydo.dnf.service;


import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletResponse;
import plus.easydo.dnf.dto.UseCdkDto;
import plus.easydo.dnf.entity.DaCdk;
import com.mybatisflex.core.service.IService;
import plus.easydo.dnf.qo.DaCdkQo;

import java.io.IOException;
import java.util.List;

/**
 * cdk配置 服务层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
public interface IDaCdkService extends IService<DaCdk> {

    List<String> add(DaCdk daCdk);

    Page<DaCdk> pageCdk(DaCdkQo cdkQo);

    Boolean removeCdk(List<String> ids);

    String useCdk(UseCdkDto useSdkDto);

    void exPortCdk(List<String> ids, HttpServletResponse response) throws IOException;
}
