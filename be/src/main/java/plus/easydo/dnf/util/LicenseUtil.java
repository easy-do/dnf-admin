package plus.easydo.dnf.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.vo.LicenseDetails;

import java.time.LocalDateTime;


/**
 * @author yuzhanfeng
 * @Date 2024-01-12 17:31
 * @Description 授权工具
 */
public class LicenseUtil {

    private LicenseUtil() {
    }

    public static void check(String license){
        LicenseDetails licenseDetails = de(license);
        LocalDateTime now = LocalDateTime.now();
        //查看是否过期
        if(LocalDateTimeUtil.between(licenseDetails.getStartTime(),now).toMillis() < 0L){
            throw new BaseException("还未到许可开始时间");
        }
        if(LocalDateTimeUtil.between(now,licenseDetails.getEndTime()).toMillis() < 0L){
            throw new BaseException("许可已过期");
        }
        //如果是在线类型的联网验证一次
        if(licenseDetails.getType() == -1){
            // do nothing
        }
    }

    public static String generate(){
        int type = 1;
        LicenseDetails licenseDetails = new LicenseDetails();
        licenseDetails.setType(type);
        LocalDateTime now = LocalDateTime.now();
        licenseDetails.setStartTime(now);
        licenseDetails.setEndTime(LocalDateTimeUtil.offset(now,30, DateUnit.DAY.toChronoUnit()));
        return type+Base64.encode(JSONUtil.toJsonStr(licenseDetails));
    }

    public static LicenseDetails de(String str){
        try {
            String type = CharSequenceUtil.sub(str, 0, 1);
            String data = CharSequenceUtil.sub(str, 1, str.length());
            if(Integer.parseInt(type) == 1){
                return JSONUtil.toBean(Base64.decodeStr(data),LicenseDetails.class);
            }else {
                // do noting
                throw new BaseException("无效的读取许可信息");
            }
        }catch (Exception e){
            throw new BaseException("无效的读取许可信息");
        }
    }

}
