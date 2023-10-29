package plus.easydo.dnf.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.entity.CharacInfo;
import plus.easydo.dnf.enums.ExpertJobJobEnum;
import plus.easydo.dnf.service.GameRoleService;
import plus.easydo.dnf.util.DictUtil;
import plus.easydo.dnf.util.FlexDataSourceUtil;
import plus.easydo.dnf.util.Latin1ConvertUtil;
import plus.easydo.dnf.util.ResultSetUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


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
    public List<CharacInfo> roleList(Integer uid, String name) {
        List<CharacInfo> list;
        try {
            DruidDataSource ds = flexDataSourceUtil.getDataSource("taiwan_cain");
            Connection conn = ds.getConnection();
            Statement stat =  conn.createStatement();
            conn.prepareStatement("SET NAMES latin1").execute();
            String sql = "SELECT * FROM charac_info";
            if(Objects.nonNull(uid) || CharSequenceUtil.isNotBlank(name)){
                sql = sql+" where ";
            }
            if(Objects.nonNull(uid)){
                sql = sql +"m_id = "+ StpUtil.getLoginIdAsInt();
            }
            if(CharSequenceUtil.isNotBlank(name)){
                if(Objects.nonNull(uid)){
                    sql = sql +" and ";
                }
                sql = sql +"charac_name like "+ Latin1ConvertUtil.convertLatin1(name);
            }
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
