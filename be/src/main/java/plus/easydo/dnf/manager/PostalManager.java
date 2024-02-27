package plus.easydo.dnf.manager;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.Postal;
import plus.easydo.dnf.mapper.PostalMapper;

import static plus.easydo.dnf.entity.table.PostalTableDef.POSTAL;

/**
 * @author laoyu
 * @version 1.0
 * @description 邮件数据库操作类
 * @date 2023/10/16
 */
@Component
public class PostalManager extends ServiceImpl<PostalMapper, Postal> {
    public boolean removeByCharacNo(Long characNo) {
        QueryWrapper query = query().and(POSTAL.RECEIVE_CHARAC_NO.eq(characNo));
        return remove(query);
    }

    public boolean clean() {
        return remove(query().where("1=1"));
    }
}
