package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import plus.easydo.dnf.dto.UseCdkDto;
import plus.easydo.dnf.qo.DaCdkQo;
import plus.easydo.dnf.service.IDaCdkService;
import plus.easydo.dnf.entity.DaCdk;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.R;

import java.io.IOException;
import java.util.List;

/**
 * cdk配置 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cdk")
public class DaCdkController {

    private final IDaCdkService daCdkService;

    /**
     * 分页查询cdk配置
     *
     * @param cdkQo 分页对象
     * @return 分页对象
     */
    @Operation(summary = "分页查询")
    @SaCheckPermission("cdk")
    @PostMapping("/page")
    public R<List<DaCdk>> pageCdk(@RequestBody DaCdkQo cdkQo) {
        return DataResult.ok(daCdkService.pageCdk(cdkQo));
    }

    /**
     * 添加cdk配置
     *
     * @param daCdk daCdk
     * @return 分页对象
     */
    @Operation(summary = "添加cdk配置")
    @SaCheckPermission("cdk.add")
    @PostMapping("/add")
    public R<List<String>> addCdk(@RequestBody DaCdk daCdk) {
        return DataResult.ok(daCdkService.add(daCdk));
    }

    /**
     * 根据cdk配置主键获取详细信息。
     *
     * @param id daCdk主键
     * @return cdk配置详情
     */
    @Operation(summary = "cdk详情")
    @SaCheckPermission("cdk")
    @GetMapping("/getInfo/{id}")
    public R<DaCdk> getCdkInfo(@PathVariable String id) {
        return DataResult.ok(daCdkService.getById(id));
    }

    /**
     * 根据主键删除cdk配置
     *
     * @param ids 主键集合
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "删除cdk")
    @SaCheckPermission("cdk.remove")
    @PostMapping("/remove")
    public R<Boolean> removeCdk(@RequestBody List<String> ids) {
        return DataResult.ok(daCdkService.removeCdk(ids));
    }

    /**
     * 根据主键导出cdk配置
     *
     * @param ids 主键集合
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @Operation(summary = "导出")
    @SaCheckPermission("cdk")
    @PostMapping("/exportCdk")
    public void exportCdk(@RequestBody List<String> ids, HttpServletResponse response) throws IOException {
        daCdkService.exPortCdk(ids,response);
    }

    /**
     * 兑换SDK
     *
     * @param useCdkDto useCdkDto
     * @return plus.easydo.dnf.vo.R<java.lang.String>
     * @author laoyu
     * @date 2024-01-04
     */
    @Operation(summary = "兑换cdk")
    @SaCheckLogin
    @PostMapping("/useCdk")
    public R<String> useCdk(@RequestBody UseCdkDto useCdkDto) {
        return DataResult.ok(daCdkService.useCdk(useCdkDto));
    }

}
