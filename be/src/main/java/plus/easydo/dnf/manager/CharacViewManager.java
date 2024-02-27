package plus.easydo.dnf.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.CharacView;
import plus.easydo.dnf.mapper.CharacViewMapper;

/**
 * @author yuzhanfeng
 * @Date 2024-01-31 10:02
 * @Description 角色选择
 */
@Component
public class CharacViewManager extends ServiceImpl<CharacViewMapper, CharacView> {
}
