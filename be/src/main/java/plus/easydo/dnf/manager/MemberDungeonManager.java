package plus.easydo.dnf.manager;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.MemberDungeon;
import plus.easydo.dnf.mapper.MemberDungeonMapper;

/**
 * @author yuzhanfeng
 * @Date 2024-01-31 10:00
 * @Description 账号副本等级
 */
@Component
public class MemberDungeonManager extends ServiceImpl<MemberDungeonMapper, MemberDungeon> {
}
