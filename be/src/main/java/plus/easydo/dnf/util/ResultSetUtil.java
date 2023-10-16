package plus.easydo.dnf.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author laoyu
 * @version 1.0
 * @description ResultSetUtil
 * @date 2023/10/16
 */

public class ResultSetUtil {

    /**
     * 将result装维实体类集合
     *
     * @param rs rs
     * @param clazz clazz
     * @return java.util.List<T>
     * @author laoyu
     * @date 2023/10/16
     */
    public static <T> List<T> reToBeanList(ResultSet rs,Class<T> clazz) throws SQLException {
        List<T> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取列的数量
        while (rs.next()) {
            JSONObject obj = JSONUtil.createObj();
            for (int i = 1; i <= columnCount; i++) {
                String columName = md.getColumnName(i);
                Object value = rs.getObject(i);
                obj.set(columName, value);
            }
            list.add(JSONUtil.toBean(obj, clazz));
        }
        return list;
    }


    public static <T> List<T> reToBeanList(ResultSet rs,Class<T> clazz, List<String> convertColum) throws SQLException {
        List<T> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            JSONObject obj = JSONUtil.createObj();
            for (int i = 1; i <= columnCount; i++) {
                String columName = md.getColumnName(i);
                Object value = rs.getObject(i);
                if(convertColum.contains(columName)){
                    value = Latin1ConvertUtil.convertUtf8(rs.getString(columName));
                }
                obj.set(StrUtil.toUnderlineCase(columName), value);
            }
            list.add(JSONUtil.toBean(obj, clazz));
        }
        return list;
    }

}
