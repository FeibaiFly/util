package com.qtone.util.mpAes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author zhengjiadong
 * 数据库手机号，加密和解密
 */
@Slf4j
@Component
public class MpAesUtil {

    private static String aesKey="bf74bbdb266b065b";
    private static String vipara="9cc38de7234b4ec5";


    /*
     * 加密 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
     */
    public static   String encode(String content) {

        try {
            // 6.根据指定算法AES自成密码器
            SecretKeySpec skySpec = new SecretKeySpec(
                    aesKey.getBytes(StandardCharsets.UTF_8), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, skySpec, new IvParameterSpec(
                    vipara.getBytes(StandardCharsets.UTF_8)));
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = content.getBytes(StandardCharsets.UTF_8);

            // 9.根据密码器的初始化方式--加密：将数据加密
            byte[] bytes = cipher.doFinal(byteEncode);
            // 10.将加密后的数据转换为字符串
            // 这里用Base64Encoder中会找不到包
            // 解决办法：
            // 在项目的Build path中先移除JRE System Library，再添加库JRE System
            // Library，重新编译后就一切正常了。
            // 11.将字符串返回
            return Base64.getEncoder().encodeToString(bytes);
        }catch (Exception e){
            log.error("sql加密错误：",e);
        }
        return content;
    }

    /*
     * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
     */
    public static String decode(String content) {

        try{
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(aesKey.getBytes(StandardCharsets.UTF_8),"AES");
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(
                    vipara.getBytes(StandardCharsets.UTF_8)));
            // 8.将加密并编码后的内容解码成字节数组
            byte[] byteContent = Base64.getDecoder().decode(content);
            /*
             * 解密
             */
            byte[] bytes = cipher.doFinal(byteContent);
            return new String(bytes, StandardCharsets.UTF_8);
        }catch (Exception e){
            log.error("sql解密错误：",e);
        }
        return content;
    }

}


