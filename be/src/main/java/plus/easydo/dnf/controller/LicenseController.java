package plus.easydo.dnf.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plus.easydo.dnf.dto.RegLicenseDto;
import plus.easydo.dnf.service.LicenseService;
import plus.easydo.dnf.vo.DataResult;
import plus.easydo.dnf.vo.LicenseDetails;
import plus.easydo.dnf.vo.R;



/**
 * 控制层。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/license")
public class LicenseController {

    private final LicenseService licenseService;

    /**
     * 许可信息
     *
     * @return plus.easydo.dnf.vo.R<plus.easydo.dnf.vo.LicenseDetails>
     * @author laoyu
     * @date 2024/1/12
     */
    @Operation(summary = "详情")
    @GetMapping("")
    public R<LicenseDetails> licenseDetails() {
        return DataResult.ok(licenseService.info());
    }

    /**
     * 许可注册
     *
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/1/12
     */
    @Operation(summary = "重置注册")
    @SaCheckPermission("license")
    @GetMapping("resetLicense")
    public R<Boolean> resetLicense() {
        return DataResult.ok(licenseService.resetLicense());
    }

    /**
     * 许可注册
     *
     * @param regLicenseDto regLicenseDto
     * @return plus.easydo.dnf.vo.R<java.lang.Boolean>
     * @author laoyu
     * @date 2024/1/12
     */
    @Operation(summary = "许可注册")
    @SaCheckPermission("license")
    @PostMapping("reg")
    public R<Boolean> regLicense(@RequestBody RegLicenseDto regLicenseDto) {
        return DataResult.ok(licenseService.regLicense(regLicenseDto));
    }

}
