package plus.easydo.dnf.qo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * cdk配置 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class DaCdkQo extends PageQo{

    /**
     * cdk码
     */
    private String cdkCode;

    /**
     * 备注
     */
    private String remark;


    /**
     * 状态 0未使用 1已使用
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 删除标记
     */
    private Boolean deleteFlag;

}
