package plus.easydo.dnf.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.enums.ExpertJobJobEnum;
import plus.easydo.dnf.manager.CharacInfoManager;
import plus.easydo.dnf.security.CurrentUserContextHolder;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.util.DictUtil;

import java.util.List;


/**
 * @author laoyu
 * @version 1.0
 * @description 游戏角色相关实现
 * @date 2023/10/14
 */
@Service
@RequiredArgsConstructor
public class GameRoleServiceImpl implements GameRoleService {

    private static JSONObject jobDictJson = JSONUtil.parseObj(DictUtil.jobDict);

    private final CharacInfoManager characInfoManager;

    @Override
    public List<CharacInfo> roleList() {
        Accounts user = CurrentUserContextHolder.getCurrentUser();
        List<CharacInfo> list = characInfoManager.listByUid(user.getUid());
        list.forEach(characInfo -> {
//            try {
//                characInfo.setCharacName(new String(characInfo.getCharacName().getBytes("latin1"),"utf8"));
//            } catch (UnsupportedEncodingException e) {
//                throw new RuntimeException(e);
//            }
            Integer job = characInfo.getJob();
            Integer growType = characInfo.getGrowType();
            Object jobName = jobDictJson.getByPath(job + "." + growType);
            characInfo.setJobName(String.valueOf(jobName));
            Integer expertJob = characInfo.getExpertJob();
            characInfo.setExpertJobName(String.valueOf(ExpertJobJobEnum.getNameByCode(expertJob)));
        });
        return list;
    }
}
