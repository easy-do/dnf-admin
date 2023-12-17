package plus.easydo.dnf.service;

import plus.easydo.dnf.entity.CharacInfo;

import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色相关
 * @date 2023/10/14
 */

public interface GameRoleService {
    List<CharacInfo> roleList(Integer uid, String name);
}
