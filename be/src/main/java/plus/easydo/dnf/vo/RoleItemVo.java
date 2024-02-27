package plus.easydo.dnf.vo;

import lombok.Data;

import java.util.List;


/**
 * 实体类。
 *
 * @author 角色背包返回参数封装
 * @since 1.0
 */
@Data
public class RoleItemVo {

    private List<GameItemVo> inventory;

    private List<GameItemVo>equipslot;

    private List<GameItemVo> creature;

    private List<GameItemVo> cargo;

    private List<GameItemVo> accountCargo;

}
