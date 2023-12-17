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
    private Long itemId;
    private Long count;
}
