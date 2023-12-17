package plus.easydo.dnf.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.Letter;
import plus.easydo.dnf.mapper.LetterMapper;

/**
 * @author laoyu
 * @version 1.0
 * @description 信件数据库操作类
 * @date 2023/10/16
 */
@Component
public class LetterManager extends ServiceImpl<LetterMapper, Letter> {

}
