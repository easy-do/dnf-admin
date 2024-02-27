package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaNoticeSendLog;
import plus.easydo.dnf.qo.PageQo;
import plus.easydo.dnf.service.IDaNoticeSendLogService;
import plus.easydo.dnf.util.WebSocketUtil;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.time.LocalDateTime;
import java.util.List;

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

    @Operation(summary = "发送公告")
    @SaCheckPermission("notice.sendNotice")
    @GetMapping("/sendNotice")
    public R<Object> sendNotice(@RequestParam("message")String message){
        WebSocketUtil.sendNotice(message);
        daNoticeSendLogService.save(DaNoticeSendLog.builder().message(message).createTime(LocalDateTime.now()).build());
        return DataResult.ok();
    }

    /**
     * 分页查询公告发送记录
     *
     * @param pageQo pageQo
     * @return com.mybatisflex.core.paginate.Page<plus.easydo.dnf.entity.DaNoticeSendLog>
     * @author laoyu
     * @date 2023/10/29
     */
    @Operation(summary = "分页查询公告发送记录")
    @SaCheckPermission("notice")
    @PostMapping("/page")
    public  R<List<DaNoticeSendLog>> pageGameNotice(@RequestBody PageQo pageQo) {
        return DataResult.ok(daNoticeSendLogService.noticePage(pageQo));
    }
}
