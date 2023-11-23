package com.qtone.util;

import com.alibaba.fastjson.JSONObject;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dao.test.TestUcMapper;
import com.qtone.util.dto.TelNumInfo;
import com.qtone.util.dto.ThirdStudentCardInfo;
import com.qtone.util.dto.ThirdUserId;
import com.qtone.util.mpAes.MpAesUtilTest;
import com.qtone.util.zxx.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试环境绑卡信息同步至正式环境
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 10:49 2023/6/25
 * @Modified By:
 */
@Service
@Slf4j
public class TestToProdCard {
    @Resource
    TestUcMapper testUcMapper;
    @Resource
    ProdUcMapper prodUcMapper;
    @Resource
    MpAesUtilTest mpAesUtilTest;
    @Resource
    HttpUtils httpUtils;

    public void testToProdCard(Integer schoolId){
        List<ThirdStudentCardInfo> thirdStudentCardInfoList = testUcMapper.getStudentCardInfo(schoolId);
        for(ThirdStudentCardInfo thirdStudentCardInfo:thirdStudentCardInfoList){
            ThirdUserId thirdUserId = prodUcMapper.getThirdUserIdByUserId(thirdStudentCardInfo.getThirdUserId());
            String imeiPhone = MpAesUtilTest.decode(thirdStudentCardInfo.getImeiPhone());
            bindStudentCard(thirdUserId.getHdktSchoolId(),thirdUserId.getStudentId(),thirdUserId.getStudentName(),thirdStudentCardInfo.getCardNum(),thirdStudentCardInfo.getImei(),
                    imeiPhone);
            System.out.println(imeiPhone);
        }

    }

    //绑卡接口
    public  void bindStudentCard(int schoolId, int studentId, String studentName, String cardNum, String imei,
                                       String imeiPhone) {
        if (schoolId <= 0 || studentId <= 0 || StringUtils.isEmpty(studentName)) {
            
            log.info("请求uc绑定卡号接口参数错误");
        }
        Map<String, Object> returnMap = null;
        try {
            String token = getRequestToken();
            String url = "http://cardapi.hejiaoyu.com.cn/uc/api/student/send/card/v2";
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("schoolId", schoolId);
            paramMap.put("userId", studentId);
            paramMap.put("userName", studentName);
            paramMap.put("userType", 2);
            paramMap.put("cardNum", cardNum);
            paramMap.put("imei", imei);
            paramMap.put("imeiPhone", imeiPhone);
            paramMap.put("saveDirectly", 0);
            log.info("请求uc绑定学生卡号接口:" + url);
            log.info("请求参数：" + JSONObject.toJSONString(paramMap));
            returnMap = httpUtils.postHttpMethodEncode(url, paramMap, token,10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String returnStr = JSONObject.toJSONString(returnMap);
        log.info("请求uc绑定卡号接口返回:" + returnMap);
    }

    //绑定亲情号接口
    public  void bindStudentOutboundCall( int studentId, String imei, String targetNameStr1,
                                 String targetNameStr2,String targetNameStr3,String targetNameStrSos,
                                 String targetTelStr1,String targetTelStr2,String targetTelStr3,String targetTelStrSos) {

        Map<String, Object> returnMap = null;
        try {
            String token = getRequestToken();
            String url = "http://cardapi.hejiaoyu.com.cn/position/api/terminalOutboundCall/saveOutbound/v2";
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("userId", 0);
            paramMap.put("realName", "手动导入");
            paramMap.put("studentId", studentId);
            paramMap.put("userType", 2);
            paramMap.put("targetNameStr1", targetNameStr1);
            paramMap.put("targetNameStr2", targetNameStr2);
            paramMap.put("targetNameStr3", targetNameStr3);
            paramMap.put("targetNameStrSos", targetNameStrSos);
            paramMap.put("targetTelStr1", targetTelStr1);
            paramMap.put("targetTelStr2", targetTelStr2);
            paramMap.put("targetTelStr3", targetTelStr3);
            paramMap.put("targetTelStrSos", targetTelStrSos);
            paramMap.put("teno", imei);
            log.info("请求uc绑定学生卡号接口:" + url);
            log.info("请求参数：" + JSONObject.toJSONString(paramMap));
            returnMap = httpUtils.postHttpMethodEncode(url, paramMap, token,10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String returnStr = JSONObject.toJSONString(returnMap);
        log.info("请求uc绑定卡号接口返回:" + returnMap);
    }

    //绑定亲情号接口
    public  Map<String,Object> bindStudentImcomingCall( String imei,Integer schoolId, List<TelNumInfo> telNumArray) {

        Map<String, Object> returnMap = null;
        try {
            String token = getRequestToken();
            String url = "http://cardapi.hejiaoyu.com.cn/position/api/terminalIncomingCall/saveIncoming/v1";
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("teno", imei);
            paramMap.put("telNumArray", telNumArray);
            paramMap.put("schoolId",schoolId);
            log.info("请求uc绑定学生呼入号码接口:" + url);
            log.info("请求参数：" + JSONObject.toJSONString(paramMap));
            returnMap = httpUtils.postHttpMethodEncode(url, paramMap, token,10000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String returnStr = JSONObject.toJSONString(returnMap);
        log.info("请求uc绑定呼入号码接口返回:" + returnMap);
        return returnMap;
    }

    public static String getRequestToken() {
        String token = EncryptUtil.encryptUser(11000, 99, 11000,
                0, "移动云中间件", "11000", 1);
        return token;
    }
}
