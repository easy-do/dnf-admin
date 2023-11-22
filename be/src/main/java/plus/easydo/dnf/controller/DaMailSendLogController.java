package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaMailSendLog;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaMailSendLogService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;

/**
 * 邮件发送记录 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/api/mailSendLog")
public class DaMailSendLogController {

    @Autowired
    private IDaMailSendLogService daMailSendLogService;



    /**
     * 分页查询邮件发送记录
     *
     * @param pageQo 分页对象
     * @return 分页对象
     */
    @SaCheckPermission("mail")
    @PostMapping("/page")
    public R<List<DaMailSendLog>> pageMailSendLog(@RequestBody  PageQo pageQo) {
        return DataResult.ok(daMailSendLogService.sendLogpage(pageQo));
    }
}
