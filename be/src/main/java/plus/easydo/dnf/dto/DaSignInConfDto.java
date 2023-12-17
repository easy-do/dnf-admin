package plus.easydo.dnf.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 *  实体类。
 *
 * @author yuzhanfeng
 * @since 2023-10-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DaSignInConfDto implements Serializable {

    private Long id;

    private String configName;

    private Date configDate;

    private List<SignInConfigDate> configJson;

    private String remark;

    private LocalDateTime signInTime;

}
