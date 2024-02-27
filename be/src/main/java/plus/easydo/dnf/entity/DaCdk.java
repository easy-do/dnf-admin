package plus.easydo.dnf.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * cdk配置 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_cdk")
public class DaCdk {

    /**
     * cdk码
     */
    @Id(keyType = KeyType.Generator, value = "UUIDKeyGenerator")
    private String cdkCode;

    /**
     * cdk类型 0默认
     */
    @Column(value = "cdk_type")
    private Integer cdkType;

    /**
     * 配置内容
     */
    @Column(value = "cdk_conf")
    private String cdkConf;

    /**
     * 备注
     */
    @Column(value = "remark")
    private String remark;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 状态 0未使用 1已使用
     */
    @Column(value = "status")
    private Boolean status;

    /**
     * 创建人
     */
    @Column(value = "create_by")
    private Long createBy;

    /**
     * 使用时间
     */
    @Column(value = "use_time")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime useTime;

    /**
     * 使用人
     */
    @Column(value = "use_user")
    private Long useUser;

    /**
     * 删除标记
     */
    private Boolean deleteFlag;

    /**
     * 生成数量
     */
    @Column(ignore = true)
    private Long number;

}
