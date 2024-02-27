package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "member_punish_info")
public class MemberPunishInfo {

    @Id(keyType = KeyType.Auto)
    private Long mId;

    private Integer punishType;

    @Column(value = "occ_time")
    private LocalDateTime occTime;

    @Column(value = "punish_value")
    private Integer punishValue;

    @Column(value = "apply_flag")
    private Integer applyFlag;

    @Column(value = "start_time")
    private LocalDateTime startTime;

    @Column(value = "end_time")
    private String endTime;

    @Column(value = "admin_id")
    private String adminId;

    @Column(value = "reason")
    private String reason;
}
