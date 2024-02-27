package plus.easydo.dnf.dto;

import lombok.Data;

/**
 * @author yuzhanfeng
 * @Date 2024-01-03 16:11
 * @Description cdk配置对象
 */
@Data
public class CdkConfDto {

    /**金币*/
    private Long gold;

    /**点券*/
    private Long bonds;

    /**物品*/
    private Long itemId;

    /**物品类型*/
    private Integer itemType;

    /**物品数量*/
    private Long itemQuantity;
}
