package plus.easydo.dnf.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.dto.DaSignInConfDto;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.qo.DaSignInConfQo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 签到相关
 * @date 2023/10/14
 */

public interface SignInService {

    /**
     * 获取签到列表
     *
     * @param characNo characNo
     * @return plus.easydo.dnf.vo.R<java.util.List<plus.easydo.dnf.entity.DaSignInConf>>
     * @author laoyu
     * @date 2023/10/14
     */
    List<DaSignInConf> signList(Integer characNo);

    /**
     * 角色签到 校验请求账户是否有该角色
     *
     * @param roleId roleId
     * @return boolean
     * @author laoyu
     * @date 2023/10/14
     */
    boolean pcCharacSign(Integer roleId);

    /**
     * 角色签到 不校验请求账户，内部调用
     *
     * @param characNo characNo
     * @author laoyu
     * @date 2023/10/14
     */
    void characSign(String channel,Integer characNo);

    Page<DaSignInConf> signInPage(DaSignInConfQo daSignInConfQo);

    DaSignInConf info(Long id);

    boolean update(DaSignInConfDto daSignInConf);

    boolean insert(DaSignInConfDto daSignInConf);
}
