package plus.easydo.dnf.service;

import com.mybatisflex.core.paginate.Page;
import plus.easydo.dnf.dto.AccountCargoDto;
import plus.easydo.dnf.dto.EditGameRoleItemDto;
import plus.easydo.dnf.dto.OtherDataDto;
import plus.easydo.dnf.entity.AccountCargo;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.entity.UserItems;
import plus.easydo.dnf.qo.CharacInfoQo;
import plus.easydo.dnf.vo.RoleItemVo;
import plus.easydo.dnf.vo.OtherDataVo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色相关
 * @date 2023/10/14
 */

public interface GameRoleService {
    List<CharacInfo> roleList(Long uid, String name);

    RoleItemVo getRoleItem(Long characNo, String type);

    Page<CharacInfo> page(CharacInfoQo characInfoQo);

    CharacInfo info(Long characNo);

    boolean update(CharacInfo characInfo);

    boolean remove(Long characNo);

    boolean recover(Long characNo);

    boolean updateRoleItem(EditGameRoleItemDto editGameRoleItemDto);

    boolean openLeftAndRight(Long characNo);

    Long onlineCount();

    OtherDataVo getOtherData(Long characNo);

    boolean setOtherData(OtherDataDto otherDataDto);

    boolean cleanItem(Long characNo, Long type);

    List<UserItems> roleItems(Long characNo);

    boolean removeItems( Long uiId);

    boolean cleanItems(Long characNo);

    void initCharacCache();

    AccountCargo getAccountCargo(Long characNo);

    boolean createAccountCargo(Long characNo);

    boolean removeAccountCargo(Long characNo);

    boolean updateAccountCargo(AccountCargoDto accountCargoDto);
}
