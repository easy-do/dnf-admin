package plus.easydo.dnf.dto;

import lombok.Builder;
import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 邮件物品信息
 * @date 2023/11/19
 */
@Data
@Builder
public class MailItemDto {
    /**物品id*/
    private Long itemId;
    /**物品类型*/
    private Integer itemType;
    /**物品数量*/
    private Long count;
}
