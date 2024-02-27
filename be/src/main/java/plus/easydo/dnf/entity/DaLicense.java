package plus.easydo.dnf.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Builder;
import lombok.Data;

/**
 * 许可信息 实体类。
 *
 * @author mybatis-flex-helper automatic generation
 * @since 1.0
 */
@Data
@Builder
@Table(value = "da_license")
public class DaLicense {

    @Id(keyType = KeyType.None)
    @Column(value = "license_key")
    private String licenseKey;

    @Column(value = "license_value")
    private String licenseValue;
}
