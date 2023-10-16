package plus.easydo.dnf.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.Accounts;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.enums.ExpertJobJobEnum;
import plus.easydo.dnf.security.CurrentUserContextHolder;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.util.DictUtil;
import plus.easydo.dnf.util.FlexDataSourceUtil;
import plus.easydo.dnf.util.ResultSetUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
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

    private final FlexDataSourceUtil flexDataSourceUtil;

    @Override
    public List<CharacInfo> roleList() {
        Accounts user = CurrentUserContextHolder.getCurrentUser();
        List<CharacInfo> list;
        try {
            DruidDataSource ds = flexDataSourceUtil.getDataSource("taiwan_cain");
            Connection conn = ds.getConnection();
            Statement stat =  conn.createStatement();
            conn.prepareStatement("SET NAMES latin1").execute();
            String sql = "SELECT * FROM charac_info where m_id = "+user.getUid();
            ResultSet rs  = stat.executeQuery(sql);
            list = ResultSetUtil.reToBeanList(rs,CharacInfo.class, Collections.singletonList("charac_name"));
            stat.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.forEach(characInfo -> {
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
