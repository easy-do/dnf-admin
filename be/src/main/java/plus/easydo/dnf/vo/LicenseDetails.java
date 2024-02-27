package plus.easydo.dnf.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author laoyu
 * @version 1.0
 * @description 许可信息
 * @date 2024/1/12
 */
@Data
public class LicenseDetails {

    /**许可类型*/
    private int type;

    /**许可开始时间*/
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**许可到期时间*/
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

}
