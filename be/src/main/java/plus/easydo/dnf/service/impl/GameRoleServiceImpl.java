package plus.easydo.dnf.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mybatisflex.core.datasource.DataSourceKey;
import com.mybatisflex.core.query.QueryChain;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import com.mybatisflex.core.row.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.mapper.CharacInfoMapper;
import plus.easydo.dnf.security.CurrentUserContextHolder;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.util.CharsetEncodingUtils;
import plus.easydo.dnf.util.DictUtil;

import java.util.List;

import static plus.easydo.dnf.entity.table.CharacInfoTableDef.CHARAC_INFO;

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

    private static JSONObject expertJobDictJson = JSONUtil.parseObj(DictUtil.expertJobDict);

    private final CharacInfoMapper characInfoMapper;

    @Override
    public List<CharacInfo> roleList() {
        Accounts user = CurrentUserContextHolder.getCurrentUser();
        List<CharacInfo> list = QueryChain.of(characInfoMapper)
                .select(CHARAC_INFO.ALL_COLUMNS)
                .from(CHARAC_INFO)
                .where(CHARAC_INFO.M_ID.ge(user.getUid()))
                .list();
        list.forEach(characInfo -> {
            characInfo.setCharacName(CharsetEncodingUtils.converter(characInfo.getCharacName()));
            Integer job = characInfo.getJob();
            Integer growType = characInfo.getGrowType();
            Object jobName = jobDictJson.getByPath(job + "." + growType);
            characInfo.setJobName(String.valueOf(jobName));
            Integer expertJob = characInfo.getExpertJob();
            Object expertJobName = expertJobDictJson.getByPath(expertJob + "");
            characInfo.setExpertJobName(String.valueOf(expertJobName));
        });
        return list;
    }
}
