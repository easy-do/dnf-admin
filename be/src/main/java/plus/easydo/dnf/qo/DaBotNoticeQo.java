package plus.easydo.dnf.qo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 通知记录 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaBotNoticeQo extends PageQo{


    /**
     * 通知类型
     */
    private String noticeType;

    /**
     * 子类型
     */
    private String subType;

    /**
     * 接收用户
     */
    private String selfUser;

    /**
     * 群组id
     */
    private String groupId;

    /**
     * 操作人
     */
    private String operatorId;

    /**
     * 发生变动人
     */
    private String userId;

    /**
     * 接收时间
     */
    private LocalDateTime selfTime;

    /**
     * 消息id
     */
    private String messageId;


}
