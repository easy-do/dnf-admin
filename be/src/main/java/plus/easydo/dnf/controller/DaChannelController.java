package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.DebugFridaDto;
import plus.easydo.dnf.dto.UpdateScriptDto;
import plus.easydo.dnf.entity.DaChannel;
import plus.easydo.dnf.qo.ChannelQo;
import plus.easydo.dnf.service.IDaChannelService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.util.List;

/**
 * 频道信息 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channel")
public class DaChannelController {

    private final IDaChannelService daChannelService;


    /**
     * 分页查询频道信息
     *
     * @param channelQo 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @SaCheckPermission("channel")
    @PostMapping("/page")
    public R<List<DaChannel>> pageChannel(@RequestBody ChannelQo channelQo) {
        return DataResult.ok(daChannelService.pageChannel(channelQo));
    }


    /**
     * 根据频道信息主键获取详细信息。
     *
     * @param id daChannel主键
     * @return 频道信息详情
     */
    @Operation(summary = "频道详情")
    @SaCheckPermission("channel")
    @GetMapping("/getInfo/{id}")
    public R<DaChannel> getChannelInfo(@PathVariable Long id) {
        return DataResult.ok(daChannelService.getById(id));
    }


    /**
     * 更新js脚本
     *
     * @param updateScriptDto updateScriptDto
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2023/12/3
     */
    @Operation(summary = "更新frida脚本")
    @SaCheckPermission("channel.frida")
    @PostMapping("/updateFridaJs")
    public R<List<String>> updateFridaJs(@RequestBody UpdateScriptDto updateScriptDto) {
       return DataResult.ok(daChannelService.updateJs(updateScriptDto));
    }
    /**
     * 更新python脚本
     *
     * @param updateScriptDto updateScriptDto
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2023/12/3
     */
    @Operation(summary = "更新python脚本")
    @SaCheckPermission("channel.frida")
    @PostMapping("/updatePythonScript")
    public R<List<String>> updatePythonScript(@RequestBody UpdateScriptDto updateScriptDto) {
       return DataResult.ok(daChannelService.updatePythonScript(updateScriptDto));
    }


    @Operation(summary = "重启frida容器")
    @SaCheckPermission("channel.frida")
    @GetMapping("/restartFrida/{id}")
    public R<List<String>> restartFrida(@PathVariable Long id) {
        return DataResult.ok(daChannelService.restartFrida(id));
    }

    @Operation(summary = "停止frida容器")
    @SaCheckPermission("channel.frida")
    @GetMapping("/stopFrida/{id}")
    public R<List<String>> stopFrida(@PathVariable Long id) {
        return DataResult.ok(daChannelService.stopFrida(id));
    }

    @Operation(summary = "获取frida的debug日志")
    @SaCheckPermission("channel.frida")
    @GetMapping("/getDebugLog/{id}")
    public R<List<String>> getDebugLog(@PathVariable Long id) {
        return DataResult.ok(daChannelService.getDebugLog(id));
    }

    @Operation(summary = "获取frida的容器日志")
    @SaCheckPermission("channel.frida")
    @GetMapping("/getFridaLog/{id}")
    public R<List<String>> getFridaLog(@PathVariable Long id) {
        return DataResult.ok(daChannelService.getFridaLog(id));
    }

    @Operation(summary = "发送frida调试信息")
    @SaCheckPermission("channel.frida")
    @PostMapping("/debugFrida")
    public R<String> debugFrida(@RequestBody DebugFridaDto debugFridaDto) {
        daChannelService.debugFrida(debugFridaDto);
        return DataResult.ok();
    }
}
