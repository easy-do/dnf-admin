package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 系统资源 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Table(value = "da_resource")
public class DaResource {

    /**
     * ID
     */
    @Id(keyType = KeyType.Auto)
    private Long id;

    /**
     * 父级ID
     */
    @Column(value = "parent_id")
    private Long parentId;

    /**
     * 资源名称
     */
    @Column(value = "resource_name")
    private String resourceName;

    /**
     * 资源编码
     */
    @Column(value = "resource_code")
    private String resourceCode;

    /**
     * 资源id路径，以/开始以/结束，从根节点ID到当点节点ID，节点ID之间以/连接
     */
    @Column(value = "resource_path")
    private String resourcePath;

    /**
     * 资源类型（M菜单 F功能）
     */
    @Column(value = "resource_type")
    private String resourceType;

    /**
     * 显示顺序
     */
    @Column(value = "order_number")
    private Integer orderNumber;

    /**
     * 路径
     */
    @Column(value = "url")
    private String url;

    /**
     * 图标
     */
    @Column(value = "icon")
    private String icon;

    /**
     * 状态（0正常 1停用）
     */
    @Column(value = "status")
    private Boolean status;

    /**
     * 是否需要授权访问：1-是、0-否
     */
    @Column(value = "auth_flag")
    private Boolean authFlag;

    /**
     * 备注
     */
    @Column(value = "resource_desc")
    private String resourceDesc;

    /**
     * 删除标记
     */
    @Column(value = "del_flag")
    private Boolean delFlag;


}
