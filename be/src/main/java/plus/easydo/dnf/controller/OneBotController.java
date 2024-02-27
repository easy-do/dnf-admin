package plus.easydo.dnf.controller;

import cn.hutool.core.io.IoUtil;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * @author laoyu
 * @version 1.0
 * @description OneBotHttp上报接收
 * @date 2024/2/25
 */
@Slf4j
@RestController
@RequestMapping("/api/oneBot")
@RequiredArgsConstructor
public class OneBotController {


//    @PostMapping("/v11/post")
    public void post(HttpServletRequest request) throws IOException {

        Enumeration<String> names = request.getHeaderNames();
        for (Iterator<String> it = names.asIterator(); it.hasNext(); ) {
            String name = it.next();
            log.info("hearder=>{},{}",name,request.getHeader(name));
        }
        log.info("body:{}",new String(getBodyByte(request)));
    }


    /**
     * 获取请问内容的字节数组
     *
     * @param request request
     * @return byte[]
     * @author laoyu
     * @date 2024-02-22
     */
    private static byte[] getBodyByte(HttpServletRequest request) throws IOException {
        ServletInputStream in = request.getInputStream();
        int length = request.getContentLength();
        ByteArrayOutputStream opt = new ByteArrayOutputStream(length);
        IoUtil.copy(in,opt);
        IoUtil.close(in);
        byte[] bodyByte = opt.toByteArray();
        IoUtil.close(opt);
        return bodyByte;
    }

}
