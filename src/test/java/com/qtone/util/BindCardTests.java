package com.qtone.util;

import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dataStatistics.month.MonthDataStatisticsService;
import com.qtone.util.dataStatistics.week.WeeklyDataStatisticsService;
import com.qtone.util.dataStatistics.week.dto.BindInfo;
import com.qtone.util.dataStatistics.week.dto.SXBindInfo;
import com.qtone.util.dto.TelNumInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BindCardTests {

    @Resource
    TestToProdCard testToProdCard;

    @Resource
    ProdUcMapper prodUcMapper;

    @Test
    public void testToProdCard() throws Exception {
        testToProdCard.testToProdCard(400043557);
    }

    @Test
    public void bindOutboundCall() throws Exception {
        String fileName = "D:\\Deskop\\智学互动\\数据导入\\王杰中学需要导入后台的呼入号码2.xlsx";
        File file = new File(fileName);
        List<String[]> files = PoiUtil.readExcel(file,18);
        List<String> notExistImei = new ArrayList<>();
        List<Integer> studentIds = new ArrayList<>();
        for(int i=1;i<files.size();i++){
            String imei = files.get(i)[6];
            Integer studentId = prodUcMapper.getStudentIdByImei(imei);
            if(studentId==null || studentId<=0){
//                notExistImei.add(imei);
                continue;
            }
            studentIds.add(studentId);
//            String targetTelName1=files.get(i)[10];
//            String targetTelName2=files.get(i)[12];
//            String targetTelName3=files.get(i)[14];
//            String targetTelNameSos=files.get(i)[8];
//            String targetTel1=files.get(i)[9];
//            String targetTel2=files.get(i)[11];
//            String targetTel3=files.get(i)[13];
//            String targetTelSos=files.get(i)[7];
//            testToProdCard.bindStudentOutboundCall(studentId,imei,targetTelName1,targetTelName2,targetTelName3,targetTelNameSos,
//                    targetTel1,targetTel2,targetTel3,targetTelSos);
//            System.out.println(studentId);
        }
//
//        System.out.println(notExistImei);
        System.out.println(studentIds);
    }

    @Test
    public void bindImcomingIn() throws Exception {
        String fileName = "D:\\Deskop\\智学互动\\数据导入\\王杰中学需要导入后台的呼入号码第二批.xlsx";
        File file = new File(fileName);
        List<String[]> files = PoiUtil.readExcel(file,20);
        List<String> notExistImei = new ArrayList<>();
        for(int i=1;i<files.size();i++){
            String imei = files.get(i)[6];
//            Integer studentId = prodUcMapper.getStudentIdByImei(imei);
//            if(studentId==null || studentId<=0){
//                notExistImei.add(imei);
//                continue;
//            }
            String targetTelName1=files.get(i)[10];
            String targetTelName2=files.get(i)[12];
            String targetTelName3=files.get(i)[14];
            String targetTelName4=files.get(i)[16];
            String targetTelName5=files.get(i)[18];
            String targetTelNameSos=files.get(i)[8];
            String targetTel1=files.get(i)[9];
            String targetTel2=files.get(i)[11];
            String targetTel3=files.get(i)[13];
            String targetTel4=files.get(i)[15];
            String targetTel5=files.get(i)[17];
            String targetTelSos=files.get(i)[7];
            List<TelNumInfo> telNumInfos = new ArrayList<>();
            if(StringUtils.isNotEmpty(targetTel1)){
                TelNumInfo telNumInfo = new TelNumInfo();
                telNumInfo.setTelName(targetTelName1);
                telNumInfo.setTargetTelNum(targetTel1);
                telNumInfos.add(telNumInfo);
            }
            if(StringUtils.isNotEmpty(targetTel2)){
                TelNumInfo telNumInfo = new TelNumInfo();
                telNumInfo.setTelName(targetTelName2);
                telNumInfo.setTargetTelNum(targetTel2);
                telNumInfos.add(telNumInfo);
            }
            if(StringUtils.isNotEmpty(targetTel3)){
                TelNumInfo telNumInfo = new TelNumInfo();
                telNumInfo.setTelName(targetTelName3);
                telNumInfo.setTargetTelNum(targetTel3);
                telNumInfos.add(telNumInfo);
            }
            if(StringUtils.isNotEmpty(targetTel4)){
                TelNumInfo telNumInfo = new TelNumInfo();
                telNumInfo.setTelName(targetTelName4);
                telNumInfo.setTargetTelNum(targetTel4);
                telNumInfos.add(telNumInfo);
            }
            if(StringUtils.isNotEmpty(targetTel5)){
                TelNumInfo telNumInfo = new TelNumInfo();
                telNumInfo.setTelName(targetTelName5);
                telNumInfo.setTargetTelNum(targetTel5);
                telNumInfos.add(telNumInfo);
            }

            Map<String,Object> returnMap=  testToProdCard.bindStudentImcomingCall(imei,10045843,telNumInfos);
            if(returnMap!=null&&StringUtils.objToInt(returnMap.get("code"))!=200){
                String str = imei+":"+ StringUtils.objToStr(returnMap.get("msg"));
                notExistImei.add(str);
            }
        }
        for(String str :notExistImei){
            System.out.println(str);
        }
    }


    //{"teno":"866930050480318","telNumArray":[{"telName":"家长","targetTelNum":"15155556666","timePeriodArray":[]},{"telName":"家长","targetTelNum":"15155556667","timePeriodArray":[]}],"schoolId":"10028366"}



}
