package plus.easydo.dnf.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.entity.DaFridaScript;
import plus.easydo.dnf.qo.FridaScriptQo;
import plus.easydo.dnf.service.IDaFridaScriptService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.Serializable;
import java.util.List;

/**
 * frida脚本信息 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fridaScript")
public class DaFridaScriptController {

    private final IDaFridaScriptService daFridaScriptService;

    /**
     * 添加 frida脚本信息
     *
     * @param daFridaScript frida脚本信息
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @Operation(summary = "添加")
    @PostMapping("/save")
    public R<Boolean> saveFridaScript(@RequestBody DaFridaScript daFridaScript) {
        return DataResult.ok(daFridaScriptService.saveFridaScript(daFridaScript));
    }



    /**
     * 根据主键更新frida脚本信息
     *
     * @param daFridaScript frida脚本信息
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @Operation(summary = "更新")
    @PostMapping("/update")
    public R<Boolean> updateFridaScript(@RequestBody DaFridaScript daFridaScript) {
        return DataResult.ok(daFridaScriptService.updateById(daFridaScript));
    }


    /**
     * 查询所有frida脚本信息
     *
     * @return 所有数据
     */
    @Operation(summary = "查询所有")
    @GetMapping("/list")
    public R<List<DaFridaScript>> listFridaScript() {
        return DataResult.ok(daFridaScriptService.list());
    }


    /**
     * 根据frida脚本信息主键获取详细信息。
     *
     * @param id daFridaScript主键
     * @return frida脚本信息详情
     */
    @Operation(summary = "详情")
    @GetMapping("/getInfo/{id}")
    public R<DaFridaScript> getFridaScriptInfo(@PathVariable Serializable id) {
        return DataResult.ok(daFridaScriptService.getById(id));
    }


    /**
     * 分页查询frida脚本信息
     *
     * @param fridaScriptQo 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页")
    @PostMapping("/page")
    public R<List<DaFridaScript>> pageFridaScript(@RequestBody FridaScriptQo fridaScriptQo) {
        return DataResult.ok(daFridaScriptService.pageFridaScript(fridaScriptQo));
    }
}
