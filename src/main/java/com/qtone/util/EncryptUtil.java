package com.qtone.util;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * @author huangguangxi
 * @ClassName Encrypt
 * @Description 对称加密算法
 * @date 2017-09-21
 */
@Slf4j
public class EncryptUtil {


    /**
     * 密钥：此处可以修改,但是长度必须是8位字符
     *
     * @Description
     */
    private static final String KEY_ALGORITHM = "QT_TOKEN";

    /**
     * 算法名称/加密模式/填充方式
     *
     * @Description
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    /**
     * 解密传入的字符串
     *
     * @param message 加密后的字符串
     * @return
     */
    public static String dencrypt(String message) {
        return dencrypt(message, KEY_ALGORITHM);
    }

    /**
     * 解密传入的字符串
     *
     * @param message 加密后的字符串
     * @param key     密钥
     * @return
     */
    public static String dencrypt(String message, String key) {
        String result = null;
        byte[] bytesrc = convertHexString(message);
        Cipher cipher;
        try {
            cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
            IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] retByte = cipher.doFinal(bytesrc);
            return new String(retByte);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 对传入的字符串加密
     *
     * @param value 待加密的字符串
     * @param key   密钥
     * @return
     */
    public static String encryptWithKey(String value, String key) {
        String result = "";
        try {
            result = toHexString(encrypt(value, key)).toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * 对传入的字符串加密
     *
     * @param value 待加密的字符串
     * @return
     */
    public static String encrypt(String value) {
        String result = "";
        try {
            //String temp = java.net.URLEncoder.encode(value, "utf-8");
            result = toHexString(encrypt(value, KEY_ALGORITHM)).toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * 用户加密
     *
     * @param userId       用户id
     * @param userType     用户类型
     * @param schoolId     学校id
     * @param parentId     家长id
     * @param reqChannel   登录渠道：WEB；TEACH；APP
     * @param provinceCode 省份代码
     * @return
     * @date 2018年4月11日 下午5:19:16
     */
    public static String encryptUser(int userId, int userType, int schoolId, int parentId,
                                     String reqChannel, String provinceCode) {
        // 加密用户信息
        String message = userId + "-" + userType + "-" + schoolId + "-"
                + System.currentTimeMillis() + "-" + parentId + "-"
                + reqChannel + "-" + provinceCode;
        return encrypt(message);
    }

    /**
     * 用户加密
     *
     * @param userId       用户id
     * @param userType     用户类型
     * @param schoolId     学校id
     * @param parentId     家长id
     * @param reqChannel   登录渠道：WEB；TEACH；APP
     * @param provinceCode 省份代码
     * @param roleId       角色id
     * @return
     * @date 2018年4月11日 下午5:19:16
     */
    public static String encryptUser(int userId, int userType, int schoolId, int parentId,
                                     String reqChannel, String provinceCode, int roleId) {
        // 加密用户信息
        String message = userId + "-" + userType + "-" + schoolId + "-"
                + System.currentTimeMillis() + "-" + parentId + "-"
                + reqChannel + "-" + provinceCode + "-" + roleId;
        return encrypt(message);
    }

    /**
     * cp用户加密
     *
     * @param [userId, userType, schoolId, parentId, reqChannel, provinceCode, roleId]
     * @return java.lang.String
     * @author liuzhichao
     * @date 2021/4/9 15:01
     */
    public static String encryptCPUser(Integer userId, Integer userType, Integer schoolId, Integer parentId,
                                       String reqChannel, String provinceCode, Integer roleId) {
        // 加密用户信息
        String message = userId + "-" + userType + "-" + schoolId + "-"
                + System.currentTimeMillis() + "-" + parentId + "-"
                + reqChannel + "-" + provinceCode + "-" + roleId;
        return encrypt(message);
    }

    /**
     * 对传入的字符串加密
     *
     * @param message 待加密的信息
     * @param key     密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    /**
     * 将字符串转换为为byte数组
     *
     * @param ss
     * @return
     */
    private static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    /**
     * 将字符串转换为以十六进制（基数 16）无符号整数形式的字符串表示形式
     *
     * @param b
     * @return
     */
    private static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }
        return hexString.toString();
    }

    /**
     * 生成后台管理员加密token
     *
     * @param userId 管理员id
     * @return
     * @author guohaibing
     */
    public static String encryptUser(int userId) {
        //加密用户信息
        String message = userId + "-" + System.currentTimeMillis();
        System.out.println("加密前：" + message);
        return encrypt(message);
    }

    /**
     * 生成后台管理员加密token
     *
     * @param userId userType schoolId
     * @return String
     * @author yuanjinxin
     */
    public static String encryptUser(int userId, int userType, int schoolId) {
        //加密用户信息
        String message = userId + "-" + userType + "-" + schoolId + "-" + System.currentTimeMillis();
        System.out.println("加密前：" + message);
        return encrypt(message);
    }

    public static void main(String[] args) {
        try {
            //String encryptUser = encryptUser(1075,1,10028193,0,"WEB","000000");
            String encryptUser = encryptUser(3136950, 1, 10038656, 0, "WX", "150000", 1);
            String dencrypt = dencrypt("F8C6F39898C90A740E19FBF0FD01C5E3110F3F0BE2D54F948179D2487991A68386F5BC022FF36D6CDE33E357C936F2F23E337C2A7D120594");
            //String token = dencrypt(dencrypt);
            System.out.println("加密======" + encryptUser);
            //System.out.println("解密="+dencrypt);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
