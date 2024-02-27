package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import plus.easydo.dnf.qo.DaGameEventQo;
import plus.easydo.dnf.service.DaGameEventService;
import plus.easydo.dnf.entity.DaGameEvent;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏事件 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gameEvent")
public class DaGameEventController {

    private final DaGameEventService daGameEventService;


    /**
     * 根据主键删除游戏事件
     *
     * @param id id
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/2/2
     */
    @Operation(summary = "删除单条")
    @SaCheckPermission("gameEvent.remove")
    @GetMapping("/remove/{id}")
    public R<Boolean> removeGameEvent(@PathVariable Serializable id) {
        return DataResult.ok(daGameEventService.removeById(id));
    }

    /**
     * 删除所有游戏事件
     *
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/2/2
     */
    @Operation(summary = "清空")
    @SaCheckPermission("gameEvent.remove")
    @GetMapping("/cleanGameEvent")
    public R<Boolean> cleanGameEvent() {
        return DataResult.ok(daGameEventService.removeAll());
    }


    /**
     * 分页查询游戏事件
     *
     * @return 分页对象
     */
    @Operation(summary = "分页")
    @SaCheckPermission("gameEvent")
    @PostMapping("/page")
    public R<List<DaGameEvent>> gameEventPage(@RequestBody DaGameEventQo daGameEventQo) {
        return DataResult.ok(daGameEventService.gameEventPage(daGameEventQo));
    }
}
