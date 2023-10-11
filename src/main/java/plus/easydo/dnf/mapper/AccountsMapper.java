package plus.easydo.dnf.mapper;

import org.apache.ibatis.annotations.Mapper;
import plus.easydo.dnf.entity.Accounts;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author yuzhanfeng
* @description 针对表【accounts】的数据库操作Mapper
* @createDate 2023-10-11 22:29:54
* @Entity plus.easydo.dnf.entity.Accounts
*/
@Mapper
public interface AccountsMapper extends BaseMapper<Accounts> {

}




