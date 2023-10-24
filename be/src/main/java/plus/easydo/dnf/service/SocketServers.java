package plus.easydo.dnf.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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



    private final SocketBusinessService socketBusinessService;

    @PostConstruct
    public void socketStart(){
        //开启一个线程启动Socket服务
        new Thread(this::socketServer).start();
    }


    private void socketServer() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("socket端口在:{}中开启并持续监听=====>", port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                //设置流读取的超时时间,这里设置为10s。超时后自动断开连接
                clientSocket.setSoTimeout(10000);
                //是否与客户端保持持续连接,这行代码在本示例中,并没有作用,因为后面的逻辑处理完成后,会自动断开连接.
                clientSocket.setKeepAlive(Boolean.TRUE);
                log.info("发现客户端连接Socket:{}:{}===========>", clientSocket.getInetAddress().getHostAddress(),
                        clientSocket.getPort());
                //这里通过线程池启动ClientHandler方法中的run方法.
                executorService.execute(new ClientHandler(clientSocket, socketBusinessService));
            }
        } catch (Exception e) {
            log.error("Socket服务启动异常!", e);
        }
    }
}
