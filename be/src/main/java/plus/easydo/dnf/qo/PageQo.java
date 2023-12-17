package plus.easydo.dnf.qo;

import lombok.Data;

/**
 * @author laoyu
 * @version 1.0
 * @description 分页参数
 * @date 2023/10/15
 */
@Data
public class PageQo {
    /**
     * 当前页
     */
    protected Integer current = 1;
    /**
     * 每页显示条数
     */
    protected Integer pageSize = 10;
}
