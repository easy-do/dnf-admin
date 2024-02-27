package plus.easydo.dnf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_bot_request")
public class DaBotRequest {

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 请求类型
     */
    @Column(value = "request_type")
    private String requestType;

    /**
     * 群组id
     */
    @Column(value = "group_id")
    private String groupId;

    /**
     * 发送用户
     */
    @Column(value = "send_user")
    private String sendUser;

    /**
     * 接收用户
     */
    @Column(value = "self_user")
    private String selfUser;

    /**
     * 接收时间
     */
    @Column(value = "self_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selfTime;

    /**
     * 验证信息
     */
    @Column(value = "comment")
    private String comment;

    /**
     * 请求标识
     */
    @Column(value = "flag")
    private String flag;


}
