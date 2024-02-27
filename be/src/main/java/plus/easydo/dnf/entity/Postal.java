package plus.easydo.dnf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import plus.easydo.dnf.config.MyInsertListener;
import plus.easydo.dnf.config.MySetListener;
import plus.easydo.dnf.config.MyUpdateListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *  邮箱。
 *
 * @author yuzhanfeng
 * @since 2023-10-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(value = "postal", dataSource = "taiwan_cain_2nd" ,onSet = MySetListener.class, onInsert = MyInsertListener.class, onUpdate = MyUpdateListener.class)
public class Postal implements Serializable {

    @Id(keyType = KeyType.Auto)
    private Long postalId;

    /**物品id*/
    private Long itemId;

    /**发送时间*/
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime occTime;

    /**接收的角色*/
    private Long receiveCharacNo;

    /**发送的角色编号*/
    private Long sendCharacNo = 0L;

    /**发送的角色名*/
    private String sendCharacName;

    /**装备时装代表耐久 道具材料代表数量 时装固定是773*/
    private Long addInfo = 1L;

    /**为装备时，表示为品级 数值与品级关系较为随机 时装是1*/
    private Integer endurance = 1;

    /**强化*/
    private Integer upgrade = 0;

    /**锻造*/
    private Integer seperateUpgrade = 0;

    /**增幅类型 空-0','异次元体力-1','异次元精神-2','异次元力量-3','异次元智力-4'*/
    private Integer amplifyOption = 0;

    /**增幅数值*/
    private Integer amplifyValue = 0;

    /**金币*/
    private Long gold;

    /**打开标识 默认是0，时装是1*/
    private Integer avataFlag = 0;

    /**上限？*/
    private Integer unlimitFlag = 1;

    /** 是否封装 */
    private Integer sealFlag = 0;

    /**生物标识 默认是0，宠物是1*/
    private Integer creatureFlag = 0;

    /**信件id*/
    private Integer letterId;

    private Integer deleteFlag = 0;

    /**物品名*/
    @Column(ignore = true)
    private String itemName;
}
