package plus.easydo.dnf.service;

import plus.easydo.dnf.dto.SignInConfigDate;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏邮箱服务
 * @date 2023/10/15
 */

public interface GamePostalService {

    /**
     * 为角色发送签到邮件
     *
     * @param roleId roleId
     * @param configData configData
     * @return boolean
     * @author laoyu
     * @date 2023/10/15
     */
    boolean sendSignInRoleMail(Integer roleId, SignInConfigDate configData);
}
