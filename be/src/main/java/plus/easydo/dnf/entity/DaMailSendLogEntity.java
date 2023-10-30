package plus.easydo.dnf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 邮件发送记录 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "da_mail_send_log", dataSource = "d_taiwan")
public class DaMailSendLogEntity {

    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 发送内容
     */
    @Column(value = "send_details")
    private String sendDetails;

    /**
     * 发送时间
     */
    @Column(value = "create_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
