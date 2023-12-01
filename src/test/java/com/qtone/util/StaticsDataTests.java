package com.qtone.util;

import com.mongodb.Mongo;
import com.qtone.util.config.mongo.MiddlewareMongoConfig;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dto.ImeiAreaInfo;
import com.qtone.util.dto.SchoolKqInfo;
import com.qtone.util.dto.TelNumInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaticsDataTests {

    @Resource
    ProdUcMapper prodUcMapper;
    @Resource(name = "middlewareMongoTemplate")
    private MongoTemplate middlewareMongoTemplate;

    /**
     * 统计江苏 22年12月-23年9月，移动云一级平台的江苏用户 绑卡人次 解绑人次，学生数
     * @throws Exception
     */
    @Test
    public void staticsJsBindCardLog() throws Exception {
        String fileName = "D:\\Deskop\\智学互动\\数据导入\\江苏月绑卡日志.xlsx";
        File file = new File(fileName);
        String[] headers = {"日期","绑卡人次","解绑人次","学生数"};
        Integer provinceId = 875;
        String startMonth = "2022-12-01 00:00:00";
        String endMonth = "2023-10-01 00:00:00";
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<10;i++){
            String newStart = DateUtils.formatDateTime(DateUtils.addMonth(DateUtils.getDate(startMonth,DateUtils.DAFAULT_DATETIME_FORMAT),i));
            String newEndMonth =  DateUtils.formatDateTime(DateUtils.addMonth(DateUtils.getDate(newStart,DateUtils.DAFAULT_DATETIME_FORMAT),1));
            int studentNum = prodUcMapper.getStudentNum(null,newEndMonth,provinceId);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("provinceId").is(provinceId);
            criteria.and("createTime").gte(newStart).lt(newEndMonth);
            criteria.and("bindAction").is(1);
            query.addCriteria(criteria);
            long bindNum = middlewareMongoTemplate.count(query,"bindCardLog");
            Query query2 = new Query();
            Criteria criteria2 = new Criteria();
            criteria2.and("provinceId").is(provinceId);
            criteria2.and("createTime").gte(newStart).lt(newEndMonth);
            criteria2.and("bindAction").is(2);
            query2.addCriteria(criteria2);
            long unBindNum = middlewareMongoTemplate.count(query2,"bindCardLog");
            String month =DateUtils.formatDate(DateUtils.getDate(newStart),"yyyy-MM");
            sb.append(month+","+bindNum+","+unBindNum+","+studentNum+"\r\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void getAllImei() throws IOException {
        Integer provinceId = 875;
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("provinceId").is(provinceId);
        query.addCriteria(criteria);
        List<ImeiAreaInfo> imeis = middlewareMongoTemplate.find(query,ImeiAreaInfo.class,"bindCardLog");
        List<ImeiAreaInfo> bindImeis = prodUcMapper.getBindImei(provinceId);
        imeis.addAll(bindImeis);
        Map<String,String> imeiMap = new HashMap<>();
        for(ImeiAreaInfo imei:imeis){
            imeiMap.put(imei.getImei(),imei.toString());
        }
        List<String> list = new ArrayList<>(imeiMap.values());
        String filePath = "D:\\Deskop\\智学互动\\imei.txt";
        writeTxtFile(filePath, list);
    }

    @Test
    public void getSchoolKqNum() throws IOException {
        Integer provinceId = 3191;


        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("provinceId").is(provinceId);
        query.addCriteria(criteria);
        List<String> imeis = middlewareMongoTemplate.findDistinct(query,"imei","bindCardLog",String.class);
        String filePath = "D:\\Deskop\\智学互动\\imei.txt";
        writeTxtFile(filePath,imeis);
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
