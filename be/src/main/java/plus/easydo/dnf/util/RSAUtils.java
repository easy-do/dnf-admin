package plus.easydo.dnf.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuzhanfeng
 * @Date 2023-11-21 17:06
 * @Description rsa工具
 */
@Slf4j
public class RSAUtils {

    private static final String RSA_PRIVATE_KEY_PREFIX = "-----BEGIN PRIVATE KEY-----";
    private static final String RSA_PRIVATE_KEY_SUFFIX = "-----END PRIVATE KEY-----";
    private static final String RSA_PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String RSA_PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";
    private static final String CONTENT_WRAP_CHAR = "\\n";
    private static final String CONTENT_WRAP_CHAR1 = "\\r";

    private static final String CONTENT_WRAP_CHAR2 = "\n";
    private static final String EMPTY_STRING = "";
    private static final String PRIVATE_KEY_PEM = "privatekey.pem";
    private static final String PUBLIC_KEY_PEM = "publickey.pem";


    /**
     * 生成秘钥对
     *
     * @return plus.easydo.dnf.util.RSAUtils.Pem
     * @author laoyu
     * @date 2023-11-22
     */
    public static Pem generateKey() {
        RSA rsa = new RSA();
       return Pem.builder().privateKey(rsa.getPrivateKeyBase64()).publicKey(rsa.getPublicKeyBase64()).build();
    }

    /**
     * 生成秘钥对
     *
     * @return plus.easydo.dnf.util.RSAUtils.Pem
     * @author laoyu
     * @date 2023-11-22
     */
    public static Pem generateKeyPem() {
        RSA rsa = new RSA();
        return Pem.builder().privateKey(getPrivatePemContent(rsa.getPrivateKeyBase64())).publicKey(getPublicPemContent(rsa.getPublicKeyBase64())).build();
    }

    /**
     * 生成秘钥对到指定路径
     *
     * @param path path
     * @author laoyu
     * @date 2023-11-22
     */
    public static void generatePemFile(String path) {
        RSA rsa = new RSA();
        writePrivateKeyByPath(path + PRIVATE_KEY_PEM, rsa.getPrivateKeyBase64());
        writePublicKeyByPath(path + PUBLIC_KEY_PEM, rsa.getPublicKeyBase64());
    }

    public static void writePublicKeyByPath(String path,String content){
        writePemContent(path, content, RSA_PUBLIC_KEY_PREFIX, RSA_PUBLIC_KEY_SUFFIX);
    }

    public static void writePrivateKeyByPath(String path,String content){
        writePemContent(path, content, RSA_PRIVATE_KEY_PREFIX, RSA_PRIVATE_KEY_SUFFIX);
    }

    private static void writePemContent(String path, String content, String prefix, String suffix) {
        File file = FileUtil.file(path);
        List<String> lines = new ArrayList<>();
        lines.add(prefix);
        String[] contents = StrUtil.split(content, 64);
        lines.addAll(List.of(contents));
        lines.add(suffix);
        FileUtil.writeLines(lines,file, CharsetUtil.defaultCharset());
    }

    private static String getPublicPemContent(String content){
        return getPemContent(content,RSA_PUBLIC_KEY_PREFIX,RSA_PUBLIC_KEY_SUFFIX);
    }

    private static String getPrivatePemContent(String content){
        return getPemContent(content,RSA_PRIVATE_KEY_PREFIX,RSA_PRIVATE_KEY_SUFFIX);
    }

    /**
     * 获取pem格式的秘钥内容
     *
     * @param content content
     * @param prefix prefix
     * @param suffix suffix
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-22
     */
    private static String getPemContent(String content,String prefix, String suffix){
        List<String> lines = new ArrayList<>();
        lines.add(prefix);
        String[] contents = StrUtil.split(content, 64);
        lines.addAll(List.of(contents));
        lines.add(suffix);
        StringBuilder stringBuilder = new StringBuilder();
        for (String line : lines) {
            stringBuilder.append(line).append(CONTENT_WRAP_CHAR2);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取pem格式的公钥内容
     *
     * @param path path
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-22
     */
    public static String publicKeyContentByPath(String path){
        String content = FileUtil.readString(path, CharsetUtil.defaultCharset()).replaceAll(CONTENT_WRAP_CHAR1,EMPTY_STRING);;
        return publicKeyContent(content);
    }

    /**
     * 获取pem格式的私钥内容
     *
     * @param path path
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-22
     */
    public static String privateKeyContentByPath(String path){
        String content = FileUtil.readString(path, CharsetUtil.defaultCharset()).replaceAll(CONTENT_WRAP_CHAR1,EMPTY_STRING);
        return privateKeyContent(content);
    }

    /**
     * 从pem格式的公钥中读取出公钥正文
     *
     * @param pubKeyContent pubKeyContent
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-22
     */
    private static String publicKeyContent(String pubKeyContent) {
        return null == pubKeyContent ? EMPTY_STRING : pubKeyContent.replaceFirst(RSA_PUBLIC_KEY_PREFIX, "").replaceFirst(RSA_PUBLIC_KEY_SUFFIX, "").replaceAll(CONTENT_WRAP_CHAR, EMPTY_STRING);
    }

    /**
     * 从pem格式的公钥中读取出私钥正文
     *
     * @param priKeyContent priKeyContent
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-22
     */
    private static String privateKeyContent(String priKeyContent) {
        return null == priKeyContent ? EMPTY_STRING : priKeyContent.replaceFirst(RSA_PRIVATE_KEY_PREFIX, "").replaceFirst(RSA_PRIVATE_KEY_SUFFIX, "").replaceAll(CONTENT_WRAP_CHAR, EMPTY_STRING);
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Pem {
        private String privateKey;
        private String publicKey;
    }
}
