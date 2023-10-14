package plus.easydo.dnf.service;

import plus.easydo.dnf.entity.DaSignInConf;

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
     * @param roleId roleId
     * @return plus.easydo.dnf.vo.R<java.util.List<plus.easydo.dnf.entity.DaSignInConf>>
     * @author laoyu
     * @date 2023/10/14
     */
    List<DaSignInConf> signList(Integer roleId);

    /**
     * 角色签到
     *
     * @param roleId roleId
     * @return boolean
     * @author laoyu
     * @date 2023/10/14
     */
    boolean roleSign(Integer roleId);
}
