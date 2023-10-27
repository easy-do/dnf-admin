package plus.easydo.dnf.handler;

import plus.easydo.dnf.vo.DpResult;

/**
 * @author laoyu
 * @version 1.0
 * @description 后台上报数据处理
 * @date 2023/10/27
 */

public interface DpReportHandler {

    /**
     * 处理上报数据并做出响应
     *
     * @param type type
     * @param value value
     * @return plus.easydo.dnf.vo.DpResult<T>
     * @author laoyu
     * @date 2023/10/27
     */
    DpResult handler(String type,String value);

}
