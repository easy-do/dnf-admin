package plus.easydo.dnf.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.PemUtil;
import cn.hutool.crypto.asymmetric.AsymmetricAlgorithm;
import cn.hutool.crypto.asymmetric.RSA;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import plus.easydo.dnf.exception.BaseException;

import javax.crypto.Cipher;
import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * @author yuzhanfeng
 * @Date 2023-11-21 17:06
 * @Description rsa工具
 */
@Slf4j
public class RSAUtils {

    private static final String RSA_PRIVATE_KEY = "PRIVATE KEY";
    private static final String RSA_PUBLIC_KEY = "PUBLIC KEY";
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
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA.getValue());
       return Pem.builder().privateKey(rsa.getPrivateKeyBase64()).publicKey(rsa.getPublicKeyBase64()).build();
    }

    /**
     * 生成秘钥对 pem格式
     *
     * @return plus.easydo.dnf.util.RSAUtils.Pem
     * @author laoyu
     * @date 2023-11-22
     */
    public static Pem generateKeyPem() {
        RSA rsa = new RSA(AsymmetricAlgorithm.RSA.getValue());
        return Pem.builder()
                .privateKey(PemUtil.toPem(RSA_PRIVATE_KEY,rsa.getPrivateKey().getEncoded()))
                .publicKey(PemUtil.toPem(RSA_PUBLIC_KEY,rsa.getPublicKey().getEncoded()))
                .build();
    }

    /**
     * 生成pem格式秘钥文件到指定路径
     *
     * @param path path
     * @author laoyu
     * @date 2023-11-22
     */
    public static void generatePemFile(String path) {
        Pem pem = generateKeyPem();
        writePrivateKeyByPath(path + PRIVATE_KEY_PEM, pem);
        writePublicKeyByPath(path + PUBLIC_KEY_PEM, pem);
    }

    /**
     * 向指定路径写入公钥信息
     *
     * @param path path
     * @param pem pem
     * @return void
     * @author laoyu
     * @date 2023-11-24
     */
    public static void writePublicKeyByPath(String path, Pem pem){
        File file = FileUtil.file(path);
        FileUtil.writeString(pem.getPublicKey(),file, CharsetUtil.defaultCharset());
    }

    /**
     * 向指定路径写入私钥内容
     *
     * @param path path
     * @param pem pem
     * @author laoyu
     * @date 2023-11-24
     */
    public static void writePrivateKeyByPath(String path, Pem pem){
        File file = FileUtil.file(path);
        FileUtil.writeString(pem.getPrivateKey(),file, CharsetUtil.defaultCharset());
    }


    /**
     * 获取pem格式证书的私钥内容
     *
     * @param path path
     * @return java.lang.String
     * @author laoyu
     * @date 2023-11-22
     */
    public static String privateKeyContentByPath(String path){
        File file = FileUtil.file(path);
        PrivateKey privateKey = PemUtil.readPemPrivateKey(FileUtil.getInputStream(file));
        return KeyUtil.toBase64(privateKey);
    }


    /**
     * 私钥加密
     *
     * @param data data
     * @param privateKey privateKey
     * @return java.lang.String
     * @author laoyu
     * @date 2023/11/23
     */
    public static String encryptByPrivateKey(String data, String privateKey) {
        try {
            Cipher cipher = Cipher.getInstance(AsymmetricAlgorithm.RSA.getValue());
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance(AsymmetricAlgorithm.RSA.getValue());
            RSAPrivateKey key =  (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(HexUtil.decodeHex(data));
            return Base64.encode(bytes);
        } catch (Exception e) {
            throw new BaseException("获取token失败:"+ ExceptionUtil.getMessage(e));
        }
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
