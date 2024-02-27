package plus.easydo.dnf.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.dto.SendMailDto;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.qo.RoleMailPageQo;



/**
 * @author yuzhanfeng
 * @Date 2024-01-05 10:44
 * @Description 游戏邮件服务
 */
public interface GameMailService {


    void sendMail(SendMailDto sendMailDto);

    Page<Postal> roleMailPage(Long characNo, RoleMailPageQo pageQo);

    boolean removeMail(Long postalId);
}
