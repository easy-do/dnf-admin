package plus.easydo.dnf.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.dto.SignInConfigDate;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.qo.RoleMailPageQo;

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

    void sendMail(SendMailDto sendMailDto);

    boolean cleanCharacMail(Long characNo);

    boolean cleanMail();

    Page<Postal> roleMailPage(Long characNo, RoleMailPageQo pageQo);

    boolean removeMail(Long postalId);
}
