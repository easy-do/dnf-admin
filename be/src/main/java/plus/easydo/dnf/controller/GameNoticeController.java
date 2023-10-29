package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.mybatisflex.core.paginate.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaNoticeSendLogEntity;
import plus.easydo.dnf.manager.CacheManager;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaNoticeSendLogService;
import plus.easydo.dnf.util.ExecCallBuildUtil;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.time.LocalDateTime;

/**
 * 公告发送记录 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequestMapping("/api/gameNotice")
public class GameNoticeController {

    @Autowired
    private IDaNoticeSendLogService daNoticeSendLogService;


    @SaCheckRole("admin")
    @GetMapping("/send")
    public R<Object> roleList(@RequestParam("message")String message){
        CacheManager.addExecList(ExecCallBuildUtil.buildNotice(message));
        daNoticeSendLogService.save(DaNoticeSendLogEntity.builder().message(message).createTime(LocalDateTime.now()).build());
        return DataResult.ok();
    }

    /**
     * 分页查询公告发送记录
     *
     * @param pageQo pageQo
     * @return com.mybatisflex.core.paginate.Page<plus.easydo.dnf.entity.DaNoticeSendLogEntity>
     * @author laoyu
     * @date 2023/10/29
     */
    @PostMapping("/page")
    public  R<Page<DaNoticeSendLogEntity>> page(@RequestBody PageQo pageQo) {
        return DataResult.ok(daNoticeSendLogService.noticePage(pageQo));
    }
}
