package plus.easydo.dnf.manager;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.entity.DaSignInConf;
import plus.easydo.dnf.mapper.DaSignInConfMapper;
import plus.easydo.dnf.qo.DaSignInConfQo;
import plus.easydo.dnf.util.LocalDateTimeUtils;

import java.sql.Date;
import java.util.List;

import static plus.easydo.dnf.entity.table.DaSignInConfTableDef.DA_SIGN_IN_CONF;
import static plus.easydo.dnf.entity.table.DaSignInLogTableDef.DA_SIGN_IN_LOG;

/**
 * @author laoyu
 * @version 1.0
 * @description 签到配置
 * @date 2023/10/16
 */
@Component
public class DaSignInConfManager extends ServiceImpl<DaSignInConfMapper, DaSignInConf> {
    public List<DaSignInConf> getRoleSignList(Integer uid, Integer roleId) {
        QueryWrapper query = new QueryWrapper()
                .select(DA_SIGN_IN_CONF.ALL_COLUMNS
                        , DA_SIGN_IN_LOG.CREATE_TIME.as("signInTime"))
                .from(DA_SIGN_IN_CONF)
                .leftJoin(DA_SIGN_IN_LOG)
                .on(DA_SIGN_IN_CONF.ID.eq(DA_SIGN_IN_LOG.CONFIG_ID)
                        .and(DA_SIGN_IN_LOG.SIGN_IN_ROLE_ID.eq(roleId))
                        .and(DA_SIGN_IN_LOG.SIGN_IN_USER_ID.eq(uid)))
                .where(DA_SIGN_IN_CONF.CONFIG_DATE.between(LocalDateTimeUtils.monthStartTime(), LocalDateTimeUtils.monthEndTime()));
        return list(query);
    }

    public DaSignInConf getByCurrentConf() {
        String date = LocalDateTimeUtil.format(LocalDateTimeUtil.now(), DatePattern.NORM_DATE_FORMATTER);
        QueryWrapper query = new QueryWrapper().where(DA_SIGN_IN_CONF.CONFIG_DATE.eq(date));
        return getOne(query);
    }

    public boolean existsByDate(Date configDate) {
        QueryWrapper queryWrapper = QueryWrapper.create().from(DA_SIGN_IN_CONF).where(DA_SIGN_IN_CONF.CONFIG_DATE.eq(configDate));
        return exists(queryWrapper);
    }

    public Page<DaSignInConf> pageByQo(DaSignInConfQo daSignInConfQo) {
        Page<DaSignInConf> page = new Page<>(daSignInConfQo.getCurrentPage(),daSignInConfQo.getPageSize());
        QueryWrapper query = QueryWrapper.create()
                .from(DA_SIGN_IN_CONF).where(DA_SIGN_IN_CONF.CONFIG_NAME.like(daSignInConfQo.getConfigName()));
        return page(page,query);
    }
}
