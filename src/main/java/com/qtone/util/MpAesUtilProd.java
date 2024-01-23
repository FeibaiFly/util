package com.qtone.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class MpAesUtilProd {

    private final static String aesKey = "bf74bbdb266b065b";

    private  final static String vipara="9cc38de7234b4ec5";
    
    public static void main(String[] args){
    	String sourceFile ="D:\\Deskop\\智学互动\\source.txt";
    	String targetFile = "D:\\Deskop\\智学互动\\result.txt";
    	readTxtFile(sourceFile,targetFile);
//        catchWord(sourceFile,catchFile,targetFile);
//        try {
//            fileNotExistImeiAndAdd();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static void fileNotExistImei() throws Exception {
        String file1path ="D:\\Deskop\\智学互动\\江苏历史imei.txt";
        String file2path = "D:\\Deskop\\智学互动\\苏州当前绑卡.txt";
        File file1 = new File(file1path);
        File file2 = new File(file2path);
        List<String> historyImei = new ArrayList<>();
        List<String> nowImei = new ArrayList<>();
        if(file1.isFile() && file1.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file1), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                historyImei.add(lineTxt);
            }
            read.close();
        }

        if(file2.isFile() && file2.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file2), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                nowImei.add(lineTxt);
            }
            read.close();
        }

        nowImei.removeAll(historyImei);
        System.out.println(nowImei);
    }

    public static void fileNotExistImeiAndAdd() throws Exception {
        String file1path ="D:\\Deskop\\智学互动\\江苏历史imei.txt";
        String file2path = "D:\\Deskop\\智学互动\\苏州当前绑卡.txt";
    	String targetFile = "D:\\Deskop\\智学互动\\result.txt";
        File file1 = new File(file1path);
        File file2 = new File(file2path);
        List<String> historyImei = new ArrayList<>();
        List<String> nowImei = new ArrayList<>();
        if(file1.isFile() && file1.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file1), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                historyImei.add(lineTxt);
            }
            read.close();
        }

        if(file2.isFile() && file2.exists()) { //判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file2), "UTF-8");//考虑到编码格式
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                nowImei.add(lineTxt);
            }
            read.close();
        }
        StringBuffer sb = new StringBuffer();
        for(String imei: nowImei){
            if(!historyImei.contains(imei)){
                sb.append(imei+ "\r\n");
            }
        }
        writeTxtFile(file1path,sb);
    }


    public static void readTxtFile(String sourceFile,String targetFile){
    	try {
            File file1 = new File(sourceFile);
            if(file1.isFile() && file1.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file1),"UTF-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                StringBuffer sb = new StringBuffer();
                while((lineTxt = bufferedReader.readLine()) != null){
                	if(lineTxt.contains(",")){
                		String [] arr = lineTxt.split(",");
                		for(int i=0;i< arr.length;i++){
                		    String str = arr[i];
//                		    if(str.contains("_")) {
//                                 String[] str2 = str.split("_");
//                                 for(int j=0;j<str2.length;j++) {
//                                     if(j==str2.length-1) {
//                                         sb.append(decode(decode(str2[j])) + ",");
//                                     }else {
//                                         sb.append(decode(decode(str2[j])) + "_");
//                                     }
//                                 }
//                                 continue;
//                            }
                            if(i>=6){
                                sb.append(decode(str)+",");
                            }else {
                                sb.append(str+",");
                            }
//                            sb.append(decode(decode(arr[i])) + ",");
                        }
                		sb.append("\r\n");
                	} else {
                		sb.append(decode(lineTxt) + "\r\n");
                	}
                }
                read.close();
                
                writeTxtFile(targetFile,sb);
                System.out.println("文件处理完毕。。。。。。。。。。");
		    }else{
		        System.out.println("找不到指定的文件");
		    }
	    } catch (Exception e) {
	        System.out.println("读取文件内容出错");
	        e.printStackTrace();
	    }
    }
    public static void writeTxtFile(String targetFile,StringBuffer sb){
    	try {
			FileOutputStream fileOutputStream = null;
			File file = new File(targetFile);
			if(!file.exists()){
			    file.createNewFile();
			}
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(sb.toString().getBytes("utf-8"));
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			System.out.println("写入文件内容出错");
			e.printStackTrace();
		}
    }

    public static void catchWord(String sourceFile1,String sourceFile2,String targetFile) {
        try {
            File file1 = new File(sourceFile1);
            File file2 = new File(sourceFile2);

            Set<String> words = new HashSet<>();
            if(file2.isFile() && file2.exists()){
                InputStreamReader read = new InputStreamReader(new FileInputStream(file2),"UTF-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    words.add(lineTxt);
                }
            }

            if(file1.isFile() && file1.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(new FileInputStream(file1),"UTF-8");//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                StringBuffer sb = new StringBuffer();
                while((lineTxt = bufferedReader.readLine()) != null){
                    if(lineTxt.contains(",")){
                        String [] arr = lineTxt.split(",");
                        String[] newArr = Arrays.copyOf(arr,arr.length+1);;
                        if(words.contains(arr[8])) {
                           newArr[arr.length] ="是";
                        }else {
                            newArr[arr.length] ="否";
                        }

                        for(int i=0;i< newArr.length;i++){
                            String str = newArr[i];
                            if(str.contains("_")) {
                                String[] str2 = str.split("_");
                                for(int j=0;j<str2.length;j++) {
                                    if(j==str2.length-1) {
                                        sb.append(decode(decode(str2[j])) + ",");
                                    }else {
                                        sb.append(decode(decode(str2[j])) + "_");
                                    }
                                }
                                continue;
                            }
                            sb.append(decode(decode(newArr[i])) + ",");
                        }
                        sb.append("\r\n");
                    } else {
                        sb.append(decode(lineTxt) + "\r\n");
                    }
                }
                read.close();

                writeTxtFile(targetFile,sb);
                System.out.println("文件处理完毕。。。。。。。。。。");
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    /*
     * 加密 1.构造密钥生成器 2.根据ecnodeRules规则初始化密钥生成器 3.产生密钥 4.创建和初始化密码器 5.内容加密 6.返回字符串
     */
    public static  String encode(String content) {
        if (null == content || "".equals(content)) {
            return content;
        }
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
            e.printStackTrace();
        }
        return content;
    }

    /*
     * 解密 解密过程： 1.同加密1-4步 2.将加密后的字符串反纺成byte[]数组 3.将加密内容解密
     */
    public static String decode(String content) {
        if (null == content || "".equals(content)) {
            return content;
        }
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
            String decodeContent = new String(bytes, StandardCharsets.UTF_8);
            if (null == decodeContent || "".equals(decodeContent)) {
                return content;
            }
            return decodeContent;
        }catch (Exception e){
//            log.error("sql解密错误：",e);
        }
        return content;
    }


}
