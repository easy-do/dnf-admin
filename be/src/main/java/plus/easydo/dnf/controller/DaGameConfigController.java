package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaGameConfig;
import plus.easydo.dnf.qo.DaGameConfigQo;
import plus.easydo.dnf.service.IDaGameConfigService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.Serializable;
import java.util.List;

/**
 * 游戏配置 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conf")
public class DaGameConfigController {

    private final IDaGameConfigService daGameConfigService;


    /**
     * 分页查询游戏配置
     *
     * @param gameConfigQo gameConfigQo
     * @return 分页对象
     */
    @SaCheckPermission("conf")
    @PostMapping("/page")
    public R<List<DaGameConfig>> pageConf(@RequestBody DaGameConfigQo gameConfigQo) {
        return DataResult.ok(daGameConfigService.confPage(gameConfigQo));
    }

    /**
     * 根据游戏配置主键获取详细信息。
     *
     * @param id daGameConfig主键
     * @return 游戏配置详情
     */
    @SaCheckLogin
    @GetMapping("/info/{id}")
    public R<DaGameConfig> getConfInfo(@PathVariable Serializable id) {
        return DataResult.ok(daGameConfigService.getById(id));
    }

    /**
     * 添加 游戏配置
     *
     * @param daGameConfig 游戏配置
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @SaCheckPermission("conf.save")
    @PostMapping("/save")
    public R<Object> saveConf(@RequestBody DaGameConfig daGameConfig) {
        return DataResult.ok(daGameConfigService.saveConf(daGameConfig));
    }

    /**
     *
     *
     *
     *
     * @param daGameConfig 游戏配置
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @SaCheckPermission("conf.update")
    @PostMapping("/update")
    public R<Object> updateConf(@RequestBody DaGameConfig daGameConfig) {
        return DataResult.ok(daGameConfigService.updateConf(daGameConfig));
    }


}
