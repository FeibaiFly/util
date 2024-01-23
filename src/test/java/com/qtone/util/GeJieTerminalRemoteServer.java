package com.qtone.util;

import com.alibaba.fastjson.JSONObject;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dto.SchoolDeviceInfo;
import com.qtone.util.zxx.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangpk
 * @Description: 批量割接电子学生证设备指向江苏二级平台的服务地址
 * @Date:Created in 10:10 2023/12/13
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class GeJieTerminalRemoteServer {
    @Resource
    ProdUcMapper prodUcMapper;
    @Resource
    HttpUtils httpUtils;

    @Test
    public void JiangSuGeJie() throws IOException {
        String deviceFile = "D:\\Deskop\\智学互动\\省份割接\\江苏割接\\地址切换设备.txt";
        List<String> imeis = TxtUtil.read(deviceFile);
        String serverUrl = "http://cardlbs.jsdict.com@9124";

        List<SchoolDeviceInfo> deviceInfos = new ArrayList<>();
        for(String str:imeis){
            String[] arr = str.split(",");
            SchoolDeviceInfo deviceInfo = new SchoolDeviceInfo();
            deviceInfo.setImei(arr[0]);
            deviceInfo.setSchoolId(Integer.valueOf(arr[1]));
            deviceInfos.add(deviceInfo);
        }
        for (SchoolDeviceInfo deviceInfo : deviceInfos) {
            Map<String, Object> returnMap = null;
            try {
                String token = TestToProdCard.getRequestToken();
                String url = "https://cardapi.hejiaoyu.com.cn/position/api/terminalServer/saveServer/v1";
                Map<String, Object> paramMap = new HashMap();
                paramMap.put("serverUrl", serverUrl);
                paramMap.put("schoolId", deviceInfo.getSchoolId());
                paramMap.put("teno", deviceInfo.getImei());
                paramMap.put("batchFlag", 0);
                log.info("请求gps修改设备指向地址接口:" + url);
                log.info("请求参数：" + JSONObject.toJSONString(paramMap));
                returnMap = httpUtils.postHttpMethodEncode(url, paramMap, token, 10000);
                System.out.println(com.alibaba.fastjson2.JSONObject.toJSONString(returnMap));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void castSuccessImei() throws IOException {
        String file = "D:\\Deskop\\智学互动\\省份割接\\江苏割接\\割接imei.txt";
        String successFile="D:\\Deskop\\智学互动\\省份割接\\江苏割接\\割接后一级平台在线.txt";
        String resultFile = "D:\\Deskop\\智学互动\\省份割接\\江苏割接\\一级平台设备割接结果.txt";
        List<String> imeis = TxtUtil.read(file);
        List<String> successImeis =TxtUtil.read(successFile);
        List<String> result = new ArrayList<>();
        for(String imei:imeis){
            if(successImeis.contains(imei)){
                imei = imei+","+"在线";
            }else {
                imei = imei+","+"离线";
            }
            result.add(imei);
        }
        TxtUtil.write(result,resultFile);
    }
}
