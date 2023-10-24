package plus.easydo.dnf.socket;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import plus.easydo.dnf.vo.DataResult;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author laoyu
 * @version 1.0
 * @description socket服务
 * @date 2023/10/24
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SocketServers {


    /**
     * 端口
     */
    @Value("${socket.port:9999}")
    private Integer port;


    @PostConstruct
    public void socketStart() {
        //开启一个线程启动Socket服务
        socketServer();
    }


    private void socketServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("服务端socket启动成功");
            Socket socket = serverSocket.accept();
            log.info("socket链接成功");
            OutputStream os = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            //保持长连接
            while (true) {
                //这里死循环不是一直在运行  如果流没数据会一直等待 所以这里可以直接用死循环
                byte[] bytes = new byte[1024];
                int length = inputStream.read(bytes);
                log.info("读取客户端消息成功:" + new String(bytes, 0, length));
                IoUtil.write(os,false, bytes);
            }
        } catch (Exception e) {
            log.info("socket链接关闭,重新开启连接");
            socketServer();
        }
    }
}
