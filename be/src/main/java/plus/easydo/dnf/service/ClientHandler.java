package plus.easydo.dnf.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.generator.SnowflakeGenerator;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

/**
 * @author laoyu
 * @version 1.0
 * @description socket客户端信息处理
 * @date 2023/10/24
 */
@Slf4j
public class ClientHandler implements Runnable {

    private final Socket clientSocket;

    private final SocketBusinessService socketBusinessService;

    private static final SnowflakeGenerator SNOWFLAKE_GENERATOR = new SnowflakeGenerator();

    public ClientHandler(Socket clientSocket, SocketBusinessService socketBusinessService) {
        this.clientSocket = clientSocket;
        this.socketBusinessService = socketBusinessService;
    }

    @Override
    @SneakyThrows
    public void run() {
        //SnowFlakeUtil 雪花ID生成工具类,后面会统一给出
        Long execId = SNOWFLAKE_GENERATOR.next();
        String hostIp = clientSocket.getInetAddress().getHostAddress();
        String port = String.valueOf(clientSocket.getPort());
        try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), Boolean.TRUE)) {
            //这里的StringUtil是自己写的工具类,后面会统一给出
            String requestInfo = IoUtil.read(clientSocket.getInputStream(), CharsetUtil.defaultCharsetName());
            if (CharSequenceUtil.isNotEmpty(requestInfo)) {
                log.info("监听到客户端消息:{},监听ID为:{}", requestInfo, execId);
                socketBusinessService.executeBusinessCode(requestInfo, execId, out);
                clientSocket.shutdownOutput();
                TimeUnit.SECONDS.sleep(1L);
            }
        } catch (IOException e) {
            log.error("与客户端:[{}:{}]通信异常!错误信息:{}", hostIp, port, e.getMessage());
        } finally {
            log.info("客户端:[{}:{}]Socket连接已关闭,日志ID为:{}========>", hostIp, port, execId);
        }
    }
}
