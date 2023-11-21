package plus.easydo.dnf.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

/**
 * @author yuzhanfeng
 * @Date 2023-11-21 17:06
 * @Description TODO
 */
@Slf4j
public class RSAUtils {

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final String RSA_PRIVATE_KEY_PREFIX = "-----BEGIN RSA PRIVATE KEY-----";
    private static final String RSA_PRIVATE_KEY_SUFFIX = "-----END RSA PRIVATE KEY-----";
    private static final String RSA_PUBLIC_KEY_PREFIX = "-----BEGIN PUBLIC KEY-----";
    private static final String RSA_PUBLIC_KEY_SUFFIX = "-----END PUBLIC KEY-----";
    private static final String CONTENT_WRAP_CHAR = "\\n";
    private static final String CONTENT_WRAP_CHAR1 = "\\r";
    private static final String CONTENT_WRAP_CHAR2 = "\n";
    private static final String EMPTY_STRING = "";

    public static void main(String[] args){
        String pu = publicKeyContentByPath("/publickey.pem");
        String pr = privateKeyContentByPath("/privatekey.pem");
        log.info("pu:{}",pu);
        log.info("pr:{}",pr);
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA.getValue(),pu,pr);
//        RSA rsa = new RSA(AsymmetricAlgorithm.RSA.getValue());
//        //获得私钥
//        String privateKeyBase64 = rsa.getPrivateKeyBase64();
//        writePrivateKeyByPath("/privatekey1.pem",privateKeyBase64);
//         //获得公钥
//        String publicKeyBase64 = rsa.getPublicKeyBase64();
//        writePublicKeyByPath("/publickey1.pem",publicKeyBase64);
    }



    public static void writePublicKeyByPath(String path,String content){
        String stringBuilder = RSA_PUBLIC_KEY_PREFIX +
                CONTENT_WRAP_CHAR2 +
                content +
                CONTENT_WRAP_CHAR2 +
                RSA_PUBLIC_KEY_SUFFIX;
        FileUtil.writeString(stringBuilder,path,CharsetUtil.defaultCharset());
    }

    public static void writePrivateKeyByPath(String path,String content){
        String stringBuilder = RSA_PRIVATE_KEY_PREFIX +
                CONTENT_WRAP_CHAR2 +
                content +
                CONTENT_WRAP_CHAR2 +
                RSA_PRIVATE_KEY_SUFFIX;
        FileUtil.writeString(stringBuilder,path,CharsetUtil.defaultCharset());
    }
    public static String publicKeyContentByPath(String path){
        String content = FileUtil.readString(path, CharsetUtil.defaultCharset()).replaceAll(CONTENT_WRAP_CHAR1,EMPTY_STRING);;
        return publicKeyContent(content);
    }
    public static String privateKeyContentByPath(String path){
        String content = FileUtil.readString(path, CharsetUtil.defaultCharset()).replaceAll(CONTENT_WRAP_CHAR1,EMPTY_STRING);
        return privateKeyContent(content);
    }

    private static String publicKeyContent(String pubKeyContent) {
        return null == pubKeyContent ? EMPTY_STRING : pubKeyContent.replaceFirst(RSA_PUBLIC_KEY_PREFIX, "").replaceFirst(RSA_PUBLIC_KEY_SUFFIX, "").replaceAll(CONTENT_WRAP_CHAR, EMPTY_STRING);
    }

    private static String privateKeyContent(String priKeyContent) {
        return null == priKeyContent ? EMPTY_STRING : priKeyContent.replaceFirst(RSA_PRIVATE_KEY_PREFIX, "").replaceFirst(RSA_PRIVATE_KEY_SUFFIX, "").replaceAll(CONTENT_WRAP_CHAR, EMPTY_STRING);
    }
}
