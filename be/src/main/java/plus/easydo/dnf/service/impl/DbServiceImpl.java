package plus.easydo.dnf.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.exception.BaseException;
import plus.easydo.dnf.service.DbService;
import plus.easydo.dnf.util.FlexDataSourceUtil;
import plus.easydo.dnf.util.ResultSetUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-01-29 17:01
 * @Description 数据库服务实现
 */
@Service
@RequiredArgsConstructor
public class DbServiceImpl implements DbService {

    private final FlexDataSourceUtil flexDataSourceUtil;

    @Override
    public List<String> databases() {
        try {
            DruidDataSource ds = flexDataSourceUtil.getDataSource("d_taiwan");
            Connection conn = ds.getConnection();
            Statement stat = conn.createStatement();
            String sql = "show databases;";
            ResultSet rs = stat.executeQuery(sql);
            List<String> res = ResultSetUtil.reToStringList(rs);
            stat.close();
            conn.close();
            return res.stream().filter(data-> data.startsWith("d_") || data.startsWith("taiwan")).toList();
        } catch (SQLException e) {
            throw new BaseException(ExceptionUtil.getMessage(e));
        }
    }
}
