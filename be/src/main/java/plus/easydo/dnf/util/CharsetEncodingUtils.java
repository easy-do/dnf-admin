package plus.easydo.dnf.util;


import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;


/**
 * @author laoyu
 * @version 1.0
 * @description 字符串转码
 * @date 2023/10/14
 */

public class CharsetEncodingUtils {

    public static String converter(String content){
        try {
            byte[] bytes = content.getBytes(StandardCharsets.ISO_8859_1);
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return content;
        }
    }
}
