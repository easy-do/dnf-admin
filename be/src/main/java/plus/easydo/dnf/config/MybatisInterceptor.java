package plus.easydo.dnf.config;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author laoyu
 * @version 1.0
 * @description TODO
 * @date 2023/10/16
 */
@Component
//拦截Executor类的query方法
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class MybatisInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object result = invocation.proceed(); //执行请求方法，并将所得结果保存到result中
//        if (result instanceof ArrayList) {
//            List list = new ArrayList();
//            ArrayList resultList = (ArrayList) result;
//            for (int i = 0; i < resultList.size(); i++) {
//                Object object = resultList.get(i);
//                if (object instanceof Integer) {
//                    continue;
//                } else {
//                    Class<?> aClass = object.getClass();
//                    //对object进行转码，然后重新赋值返回，如果其他类型需要自行根据实际情况进行处理
//                    String s = new String(JSONObject.toJSONString(object).getBytes("ISO-8859-1"), "gbk");
//                    Object object1 = JSONObject.toJavaObject(JSON.parseObject(s), aClass);
//                    list.add(object1);
//                }
//            }
//            return list;
//        }
        return result;
    }

    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    public void setProperties(Properties arg0) {
    }
}
