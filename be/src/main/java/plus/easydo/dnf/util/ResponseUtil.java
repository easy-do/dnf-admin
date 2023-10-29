package plus.easydo.dnf.util;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.CharsetUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author yuzhanfeng
 * @Date 2023/2/1 15:16
 * @Description 响应工具类
 */
public class ResponseUtil {

    private static final String FORM_DATA = "multipart/form-data";


    private static final String IMAGE_PNG = "image/png";

    private static final String FILE_NAME = "attachment;fileName=";



    private ResponseUtil() {
    }

    /**
     * 设置图片响应头
     *
     * @param response response
     * @author laoyu
     * @date 2023/2/7
     */
    public static void setImgResponse(HttpServletResponse response, String filePath) throws UnsupportedEncodingException {
        response.setContentType(IMAGE_PNG);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, FILE_NAME
                + URLEncoder.encode(CharSequenceUtil.subAfter(filePath,"/",false), CharsetUtil.UTF_8).replace("\\+", "%20"));
    }

    /**
     * 设置文件下载响应头
     *
     * @param response response
     * @author laoyu
     * @date 2023/2/7
     */
    public static void setFileResponse(HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType(FORM_DATA);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, FILE_NAME
                + URLEncoder.encode(UUID.fastUUID().toString(), CharsetUtil.UTF_8).replace("\\+", "%20"));
    }

    /**
     * 设置文件下载响应头
     *
     * @param response response
     * @author laoyu
     * @date 2023/2/7
     */
    public static void setFileResponse(HttpServletResponse response,String fileName) throws UnsupportedEncodingException {
        response.setContentType(FORM_DATA);
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, FILE_NAME
                + URLEncoder.encode(fileName, CharsetUtil.UTF_8).replace("\\+", "%20"));
    }

}
