package plus.easydo.dnf.service;

import java.io.PrintWriter;

/**
 * @author yuzhanfeng
 */
public interface SocketBusinessService {
    void executeBusinessCode(String requestInfo, Long execId, PrintWriter out);
}
