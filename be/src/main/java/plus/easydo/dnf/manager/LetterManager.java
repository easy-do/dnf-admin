package plus.easydo.dnf.manager;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.Letter;
import plus.easydo.dnf.mapper.LetterMapper;

import static plus.easydo.dnf.entity.table.LetterTableDef.LETTER;

/**
 * @author laoyu
 * @version 1.0
 * @description 信件数据库操作类
 * @date 2023/10/16
 */
@Component
public class LetterManager extends ServiceImpl<LetterMapper, Letter> {

    public boolean removeByCharacNo(Long characNo) {
        QueryWrapper query = query().and(LETTER.SEND_CHARAC_NO.eq(characNo));
        return remove(query);
    }

    public boolean clean() {
       return remove(query().and("1=1"));
    }
}
