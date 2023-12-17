package plus.easydo.dnf.dto;

import lombok.Data;


/**
 * @author laoyu
 * @version 1.0
 * @description 签到的详细配置信息
 * @date 2023/10/15
 */
@Data
public class SignInConfigDate {

    /**物品名称*/
    private String name;
    /**物品id*/
    private Long itemId;
    /**物品数量*/
    private Long quantity;
    /**物品类型*/
    private Integer itemType;

}
