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
 * 通知记录 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "da_bot_notice")
public class DaBotNotice {

    /**
     * 自增id
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 通知类型
     */
    @Column(value = "notice_type")
    private String noticeType;

    /**
     * 子类型
     */
    @Column(value = "sub_type")
    private String subType;

    /**
     * 接收用户
     */
    @Column(value = "self_user")
    private String selfUser;

    /**
     * 群组id
     */
    @Column(value = "group_id")
    private String groupId;

    /**
     * 操作人
     */
    @Column(value = "operator_id")
    private String operatorId;

    /**
     * 发生变动人
     */
    @Column(value = "user_id")
    private String userId;

    /**
     * 接收时间
     */
    @Column(value = "self_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime selfTime;

    /**
     * 消息id
     */
    @Column(value = "message_id")
    private String messageId;


}
