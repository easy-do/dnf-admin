package plus.easydo.dnf.service;

import plus.easydo.dnf.dto.RegLicenseDto;
import plus.easydo.dnf.vo.LicenseDetails;

/**
 * @author laoyu
 * @version 1.0
 * @description 许可服务
 * @date 2024/1/14
 */

public interface LicenseService {
    LicenseDetails info();

    Boolean regLicense(RegLicenseDto regLicenseDto);

    void check();

    Boolean resetLicense();

}
