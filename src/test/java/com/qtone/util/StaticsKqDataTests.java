package com.qtone.util;

import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dto.KqDeviceReportRecordLog;
import com.qtone.util.dto.KqDeviceReportSendMsgLog;
import com.qtone.util.dto.KqWorkCheckInStudentRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaticsKqDataTests {

    @Resource
    ProdUcMapper prodUcMapper;
    @Resource(name = "attendanceMongoTemplate")
    private MongoTemplate attendanceMongoTemplate;


    /**
     * 统计考门禁设备上报志包括没备名称上时间、上报事类型、上用户标识、用户名称)
     * @throws IOException
     */
    @Test
    public void getSchoolKqReportInfo() throws IOException {
        Integer schoolId =10029574;
        Integer classId =11486664;
        String startDate = "2023-11-24 00:00:00";
        String endDate = "2023-11-25 00:00:00";
        long startTime = DateUtils.getDate(startDate,DateUtils.DAFAULT_DATETIME_FORMAT).getTime();
        long endTime = DateUtils.getDate(endDate,DateUtils.DAFAULT_DATETIME_FORMAT).getTime();
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("schoolId").is(schoolId);
        criteria.and("reportTime").gte(startTime).lt(endTime);
        if(classId!=null){
            List<Integer> studentIds = prodUcMapper.getStudentIdByClassId(classId);
            criteria.and("userId").in(studentIds);
        }
        query.addCriteria(criteria);
        List<KqDeviceReportRecordLog> kqSendMsgList = attendanceMongoTemplate.find(query, KqDeviceReportRecordLog.class,"kqDeviceReportRecordLog");
        List<String> resultList = new ArrayList<>();
        for(KqDeviceReportRecordLog log:kqSendMsgList){
            StringBuffer line = new StringBuffer();
            line.append(log.getDeviceName()).append("(").append(log.getDeviceCode()).append("),").append(log.getDeviceTypeStr())
                    .append("(").append(log.getFactoryTypeStr()).append("),").append(DateUtils.formatDateTime(new Date(log.getReportTime()))).append(",")
                    .append(log.getOptStr()).append(",").append(log.getUserTag()).append(",");
            if(log.getUserId()!=null){
                line.append(log.getUserName()).append("(").append(log.getUserId()).append(")");
            }
            resultList.add(line.toString());
        }
        String filePath = "D:\\Deskop\\智学互动\\运营导出脚本\\"+schoolId+"考勤设备上报日志.txt";
        writeTxtFile(filePath,resultList);
    }
    /**
     * 考消息知日志(包括考勤学生、通知用户、通知时间、消息通道、消息标签、结果)
     * @throws IOException
     */
    @Test
    public void getSchoolKqReportSendInfo() throws IOException {
        Integer schoolId =10029574;
        String classId ="11486664";
        String startDate = "2023-11-24 00:00:00";
        String endDate = "2023-11-25 00:00:00";
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("schoolId").is(schoolId);
        criteria.and("sendTime").gte(startDate).lt(endDate);
        if(StringUtils.isNotEmpty(classId)){
            criteria.and("classId").is(classId);
        }
        criteria.and("notifyUserId").ne(null);
        query.addCriteria(criteria);
        List<KqDeviceReportSendMsgLog> kqSendMsgList = attendanceMongoTemplate.find(query, KqDeviceReportSendMsgLog.class,"kqDeviceReportSendMsgLog");
        List<String> resultList = new ArrayList<>();
        for(KqDeviceReportSendMsgLog log:kqSendMsgList){
            KqWorkCheckInStudentRecord record =  attendanceMongoTemplate.findById(log.getDataId(), KqWorkCheckInStudentRecord.class);
            StringBuffer line = new StringBuffer();
            line.append(log.getRelationUserName()).append("(").append(log.getRelationUserId()).append("),").append("家长：").append(log.getNotifyUserName())
                    .append("(").append(log.getNotifyUserTel()).append("),").append(log.getSendTime()).append(",").append(log.getSendWayValue())
                    .append(",").append(log.getMsgTagValue()).append(",").append(log.getRstStatusValue()).append(",").append(log.getSendMsg())
                    .append(",").append(record.getGradeName()).append(",").append(record.getClassName()).append(",").append(record.getDeviceName()).append(",").append(record.getDevicePosition());
            resultList.add(line.toString());
        }
        String filePath = "D:\\Deskop\\智学互动\\运营导出脚本\\"+schoolId+"考勤消息通知日志.txt";
        writeTxtFile(filePath,resultList);
    }


    private static void writeTxtFile(String filePath, List<String> textList) throws IOException {

        File file = new File(filePath);
        file.createNewFile();

        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {

            for (String text : textList) {
                bufferedWriter.write(text);
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
