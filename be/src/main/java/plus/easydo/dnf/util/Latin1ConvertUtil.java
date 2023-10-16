package plus.easydo.dnf.util;

import cn.hutool.core.util.CharsetUtil;

/**
 * @author laoyu
 * @version 1.0
 * @description Latin1和utf8互转工具
 * @date 2023/10/16
 */

public class Latin1ConvertUtil {

    private Latin1ConvertUtil() {
    }

    public static String convertUtf8(String content){
        return CharsetUtil.convert(content,"latin1","utf8");
    }

    public static String convertLatin1(String content){
        return CharsetUtil.convert(content,"utf8","latin1");
    }
}
