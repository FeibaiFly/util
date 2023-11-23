package com.qtone.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author zhengjiadong
 */
@Slf4j
public class AesUtil {

    //密钥
    public static String AES_KEY ="ee7739e9f26c2c7a";
    //偏移量
    public static String VIPARA ="04e5f6fa374111a2";

    /*
     * 加密 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
     */
    @SneakyThrows
    public static String AESEncode(String content) {
        if(StringUtils.isBlank(content)){
            return content;
        }
        // 6.根据指定算法AES自成密码器
        SecretKeySpec skeySpec = new SecretKeySpec(
                AES_KEY.getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(
                VIPARA.getBytes("utf-8")));
        // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
        byte[] byte_encode = content.getBytes("utf-8");

        // 9.根据密码器的初始化方式--加密：将数据加密
        byte[] byte_AES = cipher.doFinal(byte_encode);
        // 10.将加密后的数据转换为字符串
        // 这里用Base64Encoder中会找不到包
        // 解决办法：
        // 在项目的Build path中先移除JRE System Library，再添加库JRE System
        // Library，重新编译后就一切正常了。
        String AES_encode = org.apache.commons.codec.binary.Base64.encodeBase64String(byte_AES);
        // 11.将字符串返回
        return AES_encode;
    }

    /*
     * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
     */
    @SneakyThrows
    public static String AESDecode(String content) {
        if(StringUtils.isBlank(content)){
            return content;
        }
        // 5.根据字节数组生成AES密钥
        SecretKey key = new SecretKeySpec(AES_KEY.getBytes("utf-8"),
                "AES");
        // 6.根据指定算法AES自成密码器
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(
                VIPARA.getBytes("utf-8")));
        // 8.将加密并编码后的内容解码成字节数组
        byte[] byte_content =  org.apache.commons.codec.binary.Base64.decodeBase64(content);
        /*
         * 解密
         */
        byte[] byte_decode = cipher.doFinal(byte_content);
        String AES_decode = new String(byte_decode, "utf-8");
        return AES_decode;
    }

    public static void main(String[] args) {
//        String content = "{\n" +
//                "    \"phone\":\"15719327609\"\n" +
//                "}";
//        content = AESEncode(content);
//        System.out.println(content);
        String content = "xf6JZtqfturxIfqSpQNDoqxQudJBwo1GBM7d2Kgzu7I=";
        content = AESEncode(content);
        System.out.println(content);
    }
}


