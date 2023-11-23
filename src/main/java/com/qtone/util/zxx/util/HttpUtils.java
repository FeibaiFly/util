package com.qtone.util.zxx.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qtone.util.AesUtil;
import com.qtone.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Component
public class HttpUtils {
    private static final Logger logger = LogManager.getLogger(HttpUtils.class);

    @Autowired
    private Environment environment;

    /**
     * 发送HttpPost请求
     *
     * @param strURL   url地址
     * @param paramMap 传参，Map对象
     * @param token    token,访问hdkt项目时,传token;访问极光推送时,token为null
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postHttpMethod(String strURL, Map<String, Object> paramMap, String token) {
        // paramMap解析为字符串
        String params = null;
        try {
            params = JSONObject.toJSONString(paramMap);
        } catch (Exception e1) {
            e1.printStackTrace();
            logger.error("map转换json字符串失败");
            return null;
        }
        HttpURLConnection connection = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            URL url = new URL(strURL);// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            if (null != token) {
                connection.setRequestProperty("token", token); // 设置请求头，token
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            InputStream inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\r\n");
                line = br.readLine();
            }
            br.close();
            inputStream.close();
            resultMap = (Map<String, Object>) JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            logger.info("=====发送error!==========");
            e.printStackTrace();
            return null;
        }
        logger.info("=====发送success!==========");
        return resultMap;
    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL   url地址
     * @param paramMap 传参，Map对象
     * @param token    token,访问hdkt项目时,传token;访问极光推送时,token为null
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> postHttpMethod(String strURL, String params, String token) {
        // paramMap解析为字符串

        HttpURLConnection connection = null;
        Map<String, Object> resultMap = new HashMap<>();
        try {
            URL url = new URL(strURL);// 创建连接
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            if (null != token) {
                connection.setRequestProperty("token", token); // 设置请求头，token
            }
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            // 读取响应
            InputStream inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            StringBuffer sb = new StringBuffer();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\r\n");
                line = br.readLine();
            }
            br.close();
            inputStream.close();
            resultMap = (Map<String, Object>) JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            logger.info("=====发送error!==========");
            e.printStackTrace();
            return null;
        }
        logger.info("=====发送success!==========");
        return resultMap;
    }

    public static Map<String, Object> getHttpMethod(String url) {
        StringBuffer result = new StringBuffer();
        Map<String, Object> resultMap = new HashMap<>();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            resultMap = (Map<String, Object>) JSONObject.parse(result.toString());
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return resultMap;
    }

    public static List<Map<String, Object>> getHttpMethod2(String url) {
        StringBuffer result = new StringBuffer();
        List<Map<String, Object>> resultMap = new ArrayList<>();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            resultMap = JSONObject.parseArray(result.toString());
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        return resultMap;
    }

    public static void downFile(String downUrl, String fileName, String targetPath) throws Exception {
        URL url = new URL(downUrl);
        // 获取文件后缀名
//        String fileName = "";
        int index = url.getFile().lastIndexOf(".");
        if (index != -1) {
            int index2 = url.getFile().lastIndexOf("?");
            if (index2 > 0) {
                fileName += url.getFile().substring(index, index2);
            } else {
                fileName += url.getFile().substring(index);
            }

        }

        // 打开地址
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        // 获取流
        InputStream is = urlConnection.getInputStream();

        // 写入流
        Random random = new Random();
        FileOutputStream fos = new FileOutputStream(targetPath + fileName);

        // 写入文件
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        // 关闭流
        fos.close();
        is.close();
        urlConnection.disconnect(); // 断开连接
    }


    /**
     * create by: zhangpk
     * description: post请求加解密
     * create time: 11:23 2021/10/28
     *
     * @return
     * @Param: null
     */
    public  Map<String, Object> postHttpMethodEncode(String strURL, Map<String, Object> paramMap, String token, int timeOut)
            throws Exception {
        //是否加密，true为加密

        // paramMap解析为字符串
        String params = JSON.toJSONString(paramMap);
        params = AesUtil.AESEncode(params);
        HttpURLConnection connection = null;
        Map<String, Object> resultMap = new HashMap<>();
        URL url = new URL(strURL);// 创建连接
        connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("POST"); // 设置请求方式
        connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
        connection.setConnectTimeout(timeOut);
        connection.setReadTimeout(timeOut);
        if (null != token) {
            connection.setRequestProperty("token", token); // 设置请求头，token
        }
        connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式0
//        String version = PropertiesUtil.getPropertiesByKey("DECRYPT_VERSION");
//        connection.setRequestProperty("X-Request-VID",version);
        connection.connect();
        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
        out.append(params);
        out.flush();
        out.close();
        // 读取响应
        InputStream inputStream = connection.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
        StringBuffer sb = new StringBuffer();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append("\r\n");
            line = br.readLine();
        }
        br.close();
        inputStream.close();
        resultMap = JSONObject.parseObject(sb.toString(), Map.class);

        if (resultMap != null && StringUtils.objToInt(resultMap.get("code")) == 200) {
            Object encodeDate = resultMap.get("data");
            if (StringUtils.isNotNullToObj(encodeDate)) {
                resultMap.put("data", AesUtil.AESDecode(StringUtils.objToStr(encodeDate)));

            }

        }

        return resultMap;
    }

    public static void main(String[] args) throws Exception {
        String fileUrl = "https://video.cache.bdschool.cn/vd/6119be75ae7baae109319e9a406685b7.mp4?s=a0c9b26100d7781f0e3ae187c6fbe9fd&c=656224&id=58109";
        downFile(fileUrl, "test", "D:\\中小学视屏资源\\");
    }
}
