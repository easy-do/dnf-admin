package plus.easydo.dnf.service.impl;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import plus.easydo.dnf.service.SocketBusinessService;
import plus.easydo.dnf.vo.DataResult;

import java.io.PrintWriter;

/**
 * @author laoyu
 * @version 1.0
 * @description socket消息业务处理
 * @date 2023/10/24
 */
@Slf4j
@Service
public class SocketBusinessServiceImpl implements SocketBusinessService {


    @Override
    public void executeBusinessCode(String requestInfo, Long execId, PrintWriter printWriter) {

        DataResult<String> response;
        try {
            if (CharSequenceUtil.isEmpty(requestInfo)) {
                return;
            }
            response = DataResult.okMsg("success");
        } catch (Exception e) {
            response = DataResult.fail();
            log.error("Socket客户端数据返回异常!当前日志ID:[{}],异常信息:{}", execId, ExceptionUtil.getMessage(e));
        }
        //将响应报文通过PrintWriter回写给客户端
        printWriter.println(JSONUtil.toJsonPrettyStr(response));
    }
}
