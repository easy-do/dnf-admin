package plus.easydo.dnf.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.dto.RegLicenseDto;
import plus.easydo.dnf.entity.DaLicense;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.mapper.DaLicenseMapper;
import plus.easydo.dnf.service.CaptchaService;
import plus.easydo.dnf.service.LicenseService;
import plus.easydo.dnf.util.LicenseUtil;
import plus.easydo.dnf.vo.LicenseDetails;

import java.util.Objects;

/**
 * @author laoyu
 * @version 1.0
 * @description 许可服务
 * @date 2024/1/14
 */
@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final CaptchaService captchaService;

    private final DaLicenseMapper daLicenseMapper;

    private static final String LICENSE_TEXT = "license_text";


    private String getLicense(){
        DaLicense daLicense = daLicenseMapper.selectOneById(LICENSE_TEXT);
        if(Objects.isNull(daLicense)){
            String license = LicenseUtil.generate();
            setLicense(license);
            return license;
        }
        return daLicense.getLicenseValue();
    }

    private boolean setLicense(String license){
        daLicenseMapper.deleteById(LICENSE_TEXT);
        DaLicense entity = DaLicense.builder().licenseKey(LICENSE_TEXT).licenseValue(license).build();
        return daLicenseMapper.insert(entity) > 0;
    }

    @Override
    public LicenseDetails info() {
        return LicenseUtil.de(getLicense());
    }

    @Override
    public Boolean regLicense(RegLicenseDto regLicenseDto) {
        if(!captchaService.verify(regLicenseDto.getCaptchaKey(),regLicenseDto.getVerificationCode())){
            throw new BaseException("验证码错误");
        }
        LicenseUtil.check(regLicenseDto.getLicense());
        setLicense(regLicenseDto.getLicense());
        return true;
    }

    @Override
    public void check() {
        LicenseUtil.check(getLicense());
    }

    @Override
    public Boolean resetLicense() {
        String license = LicenseUtil.generate();
        return setLicense(license);
    }

}
