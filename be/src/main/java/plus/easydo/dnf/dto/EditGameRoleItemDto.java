package plus.easydo.dnf.dto;

import lombok.Data;
import plus.easydo.dnf.vo.GameItemVo;

/**
 * @author laoyu
 * @version 1.0
 * @description 编辑角色物品信息参数封装
 * @date 2023/12/29
 */
@Data
public class EditGameRoleItemDto {

    private Long characNo;

    private String type;

    private GameItemVo gameItemVo;
}
