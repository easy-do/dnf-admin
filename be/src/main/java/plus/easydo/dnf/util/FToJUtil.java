package plus.easydo.dnf.util;

import com.ibm.icu.text.Transliterator;

/**
 * @author laoyu
 * @version 1.0
 * @description 繁体转简体工具
 * @date 2023/11/20
 */
public class FToJUtil {

    private FToJUtil() {
    }

    private static final Transliterator TRANSLITERATOR = Transliterator.getInstance("Traditional-Simplified");
    public static String FtoJ(String f){
        return TRANSLITERATOR.transliterate(f);
    }
}
