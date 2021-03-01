package test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;


public class DESUtils {
    /** 算法名称 */
    private static final String KEY_ALGORITHM = "DES";

    /** 算法名称/加密模式/填充方式 */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    /** 8位KEY 默认 */
    private static final String DEFAULT_KEY = "12345678";
    /** 8位IV 默认 */
    private static final String DEFAULT_IV = "1qaz2wsx";

    /**
     * 生成密钥(base64字符串)
     */
    public static String initKey() throws NoSuchAlgorithmException, NoSuchProviderException {
        // 实例化密钥生成器
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        String name = kg.getProvider().getName();
        System.out.println(name);
        // 生成密钥
        SecretKey secretKey = kg.generateKey();
        // 获取二进制密钥编码形式
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    /**
     * 转换密钥
     */
    private static Key toKey(String key) throws Exception {
        // 实例化Des密钥
      //  DESKeySpec dks = new DESKeySpec(Base64.decodeBase64(key));
        DESKeySpec dks = new DESKeySpec(Base64.getDecoder().decode(key));
        // 实例化密钥工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        // 生成密钥
        return keyFactory.generateSecret(dks);
    }

    /**
     * 加密数据
     *
     * @param data 待加密数据
     * @param key  密钥(8位字符的base64)
     * @param iv   加密向量(8位字符的base64)
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key, String iv) throws Exception {
        // 还原密钥
        Key k = toKey(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, k, ivParameterSpec);
        // 执行加密操作。加密后的结果通常都会用Base64编码进行传输
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    public static String encrypt(String data, String key) throws Exception {
        String iv = Base64.getEncoder().encodeToString(DEFAULT_IV.getBytes(StandardCharsets.UTF_8));
        return encrypt(data, key, iv);
    }

    public static String encrypt(String data) throws Exception {
        String key = Base64.getEncoder().encodeToString(DEFAULT_KEY.getBytes(StandardCharsets.UTF_8));
        String iv = Base64.getEncoder().encodeToString(DEFAULT_IV.getBytes(StandardCharsets.UTF_8));
        return encrypt(data, key, iv);
    }

    /**
     * 解密数据
     *
     * @param data 待解密数据(base64字符串)
     * @param key  密钥(8位字符的base64)
     * @param iv   解密向量(8位字符的base64)
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key, String iv) throws Exception {
        Key k = toKey(key);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(iv));
        // 初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, k, ivParameterSpec);
        // 执行解密操作
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
    }

    public static String decrypt(String data) throws Exception {
        String key = Base64.getEncoder().encodeToString(DEFAULT_KEY.getBytes(StandardCharsets.UTF_8));
        String iv = Base64.getEncoder().encodeToString(DEFAULT_IV.getBytes(StandardCharsets.UTF_8));
        return decrypt(data, key, iv);
    }

    public static String decrypt(String data, String key) throws Exception {
        //String iv = Base64.encodeBase64String(DEFAULT_IV.getBytes(StandardCharsets.UTF_8));
        String iv = Base64.getEncoder().encodeToString(DEFAULT_IV.getBytes(StandardCharsets.UTF_8));
        return decrypt(data, key, iv);
    }

    public static void main(String[] args) throws Exception {
        String source = "永无bug";
        System.out.println("原文: " + source);

        String key = initKey();
        System.out.println("密钥: " + key);

    //    String encryptData = encrypt(source, key, key);
        String encryptData ="mWp6K0P1kjnZK2y9EExDxOTOT0Ag/skV0A5ptGVo5zKa4nFm6TieJvfaXyT0vPlbC1W4RdT39Z2499tQ3GFAHRtflYw8koxZsqRK2z9XOSSzr15xdoGjt+dKP90xToNhiTEtj6CYZOGTKX5GjozkOtudcwy62lD+WxDjkToxL559zWWQNvdgs4+plQlb+locahZ9/BYt8PXPUg7BNjoZZk69iOtj7TXPsyT6SZb5XDWBAW2LNeB+rmwnNQuxTExGsFySuEFOmSMiURJYMorXexxHN+27S6QoSCVW/EhvXw+wgYz6io0SrB95a35lMBoGQ8ztrzRdp+Qy3QkNyaTg5407vzruIvIs5l3mwZemwnREUMDXsxxwmchGSvxmz68gozLeNuVyWSMACWALmPOmR2ENZoYkRUP9naox2OzoThKaIjtXEh7pZ7Tlx2iuOTsFxLIW51KuM9AHCU8HPexsIm8SoPga6WeeAhwsCIBgwE90FVQQXFmEvQ==";
        System.out.println("加密: " + encryptData);

        String decryptData = decrypt(encryptData);
        System.out.println("解密: " + decryptData);
    }
}