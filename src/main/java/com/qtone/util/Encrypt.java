package com.qtone.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 15:21 2022/12/5
 * @Modified By:
 */
public class Encrypt {
    private static final String IV = "5075428636499153";

    public static String encrypt(String strKey, String strIn) throws Exception {
        SecretKeySpec skeySpec = getKey(strKey);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(IV.getBytes("UTF-8"));
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(strIn.getBytes("UTF-8"));
        return org.apache.commons.codec.binary.Base64.encodeBase64String(encrypted);
    }
    private static SecretKeySpec getKey(String strKey) throws Exception {
        byte[] arrBTmp = strKey.getBytes("UTF-8");
        byte[] arrB = new byte[16];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        SecretKeySpec skeySpec = new SecretKeySpec(arrB, "AES");
        return skeySpec;
    }

    public static String generateSignature(final Map<String, String> data, String key, SignType signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals("sign")) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (SignType.MD5.equals(signType)) {
            return MD5(sb.toString()).toUpperCase();
        } else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 生成 MD5
     *
     * @param data 待处理数据
     * @return MD5结果
     */
    public static String MD5(String data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] array = md.digest(data.getBytes("UTF-8"));
        StringBuilder sb = new StringBuilder();
        for (byte item : array) {
            sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString().toUpperCase();
    }

    //加密类型
    public enum SignType {
        MD5, HMACSHA256
    }

    public static void main(String[] args) throws Exception {
        String singnKey = "COHeJfoWQgaYBuna";
        String appKey ="527a81f5e4c7ea2107994606e510c2a2";
        //加密前请求参数
        String data = "{\n" +
                "    \"imei\":\"1003220368569\",\n" +
                "}";
        //加密后
        data = URLEncoder.encode(encrypt(singnKey, data), "utf-8");
        System.out.println(data);
        Map<String, String> dataMap = new HashMap();
        dataMap.put("appKey", appKey);
        dataMap.put("data", data);
        String authorization = generateSignature(dataMap, appKey, SignType.MD5);
        System.out.println();
        System.out.println(authorization);
    }


}
