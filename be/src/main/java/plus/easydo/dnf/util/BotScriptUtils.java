package plus.easydo.dnf.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.io.File;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2024-02-23 15:37
 * @Description 机器人脚本扩展工具,定义一些便捷方法
 */
public class BotScriptUtils {

    private BotScriptUtils() {

    }

    /**
     * 判断一个逗号分割的字符串 转为数组后是否包含对应的字符
     *
     * @param listStr listStr
     * @param str str
     * @return boolean
     * @author laoyu
     * @date 2024-02-23
     */
    public static boolean listStrContains (String listStr,String str){
        return CharSequenceUtil.split(listStr,",").contains(str);
    }

    /**
     * 删除一个逗号分割的字符串内的某个元素
     *
     * @param listStr listStr
     * @param str str
     * @return java.lang.String
     * @author laoyu
     * @date 2024/2/23
     */
    public static String removeListStr (String listStr,String str){
        List<String> list = CharSequenceUtil.split(listStr, ",");
        list.remove(str);
        return CharSequenceUtil.join(",",list);
    }


    /**
     * 获得艾特后面的消息文本
     *
     * @param message message
     * @return java.lang.String
     * @author laoyu
     * @date 2024-02-23
     */
    public static String atMessageFe (String message){
        return CharSequenceUtil.subAfter(message, "]", false);
    }


    public static boolean isLong(String text){
        try {
            Long.parseLong(text);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getImageUrlFor302(String url){
        HttpResponse res = HttpRequest.get("").execute();
        String location = res.header("Location");
        if(CharSequenceUtil.isNotBlank(location)){
            return location;
        }
        return null;
    }

    public static void main(String[] args) {
        List<File> files = FileUtil.loopFiles("");
        for (int i = 0; i < files.size(); i++) {
            FileUtil.rename(files.get(i),(i+1)+".JPG",true);
        }
    }

}
