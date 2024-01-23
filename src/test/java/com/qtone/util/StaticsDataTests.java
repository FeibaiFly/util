package com.qtone.util;

import com.mongodb.Mongo;
import com.qtone.util.config.mongo.MiddlewareMongoConfig;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dto.*;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StaticsDataTests {

    @Resource
    ProdUcMapper prodUcMapper;
    @Resource(name = "middlewareMongoTemplate")
    private MongoTemplate middlewareMongoTemplate;
    @Resource(name = "gpsMongoTemplate")
    private MongoTemplate gpsMongoTemplate;
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

    /**
     * 统计江苏当前在网学生信息
     * @throws IOException
     */
    @Test
    public void getJSUserInfo() throws IOException {
        Integer provinceId = 875;
        List<HistoryImeiInfo> userInfos = prodUcMapper.getStudentBindInfo(provinceId,1);
        List<String> list = new ArrayList<>();
        for(HistoryImeiInfo imeiInfo:userInfos){
            list.add(imeiInfo.toString());
        }
        String filePath = "D:\\Deskop\\智学互动\\source.txt";
        writeTxtFile(filePath, list);
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
    public void getHistoryImei() throws IOException {
        Integer provinceId = 875;
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("provinceId").is(provinceId);
        query.addCriteria(criteria);
        List<HistoryImeiInfo> imeis = middlewareMongoTemplate.find(query,HistoryImeiInfo.class,"bindCardLog");
        List<HistoryImeiInfo> userInfos = prodUcMapper.getStudentBindInfo(provinceId,null);
        Map<Integer,HistoryImeiInfo> userMap = userInfos.stream().collect(Collectors.toMap(HistoryImeiInfo::getUserId, Function.identity(), (key1, key2) -> key2));
        Set<Integer> userIds =new HashSet<>();
        for(HistoryImeiInfo imeiInfo:imeis){
            HistoryImeiInfo user = userMap.get(imeiInfo.getUserId());
            if(user!=null){
                imeiInfo.setProvinceName(user.getProvinceName());
                imeiInfo.setCityName(user.getCityName());
                imeiInfo.setDistrictName(user.getDistrictName());
                imeiInfo.setSchoolName(user.getSchoolName());
                imeiInfo.setUserName(user.getUserName());
                imeiInfo.setIsStudentExist(1);
                imeiInfo.setParentTel(user.getParentTel());
                imeiInfo.setImeiPhone(user.getImeiPhone());
                userIds.add(imeiInfo.getUserId());
            }
        }
        for(HistoryImeiInfo userInfo:userInfos){
            if(!userIds.contains(userInfo.getUserId()) && StringUtils.isNotBlank(userInfo.getImei())){
                imeis.add(userInfo);
            }
        }

        List<String> list = new ArrayList<>();

        for(HistoryImeiInfo imeiInfo:imeis){
            String str = imeiInfo.toString();
            list.add(str);
        }
        String filePath = "D:\\Deskop\\智学互动\\source.txt";
        writeTxtFile(filePath, list);
    }


    public void removeRepeatImei() throws IOException {
        String sourceFile ="D:\\Deskop\\智学互动\\source.txt";
        String targetFile = "D:\\Deskop\\智学互动\\result.txt";
        List<String> sourceList = TxtUtil.read(sourceFile);
        Map<String,String> targetMap = new HashMap<>();
        for(String source:sourceList){
            String[] str = source.split("\t");
            String imei = str[7];
            targetMap.put(imei,source);
        }
        List<String> targetList = new ArrayList<>(targetMap.values());
        TxtUtil.write(targetList,targetFile);

    }

    public static void main(String[] args) throws IOException {
        String sourceFile ="D:\\Deskop\\智学互动\\source.txt";
        String targetFile = "D:\\Deskop\\智学互动\\result.txt";
        List<String> sourceList = TxtUtil.read(sourceFile);
        Map<String,String> targetMap = new HashMap<>();
        for(String source:sourceList){
            String[] str = source.split(",");
            String imei = str[7];
            targetMap.put(imei,source);
        }
        List<String> targetList = new ArrayList<>(targetMap.values());
        TxtUtil.write(targetList,targetFile);
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

    /**
     * 统计全国用户定位次数
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2024/1/23 11:28
     */
    @Test
    public void getLocationNum() throws IOException {
        String startTime = "2024-01-01 00:00:00";
        String endTime ="2024-01-24 00:00:00";
        Date start =DateUtils.getDate(startTime,DateUtils.DAFAULT_DATETIME_FORMAT);
        Date end = DateUtils.getDate(endTime,DateUtils.DAFAULT_DATETIME_FORMAT);
        int day = DateUtils.daysBetweenDate(start, end);
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            endTime = String.format("%08x",newEnd.getTime()  / 1000) + "0000000000000000";
            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$userId").as("userId")
                    .and("$locationNum").as("locationNum")
                    .and("$schoolId").as("schoolId");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("userId")
                            .count().as("locationNum")
                            .last("schoolId").as("schoolId")
                            .last("userId").as("userId"),
                    projectionOperation
            );
            AggregationResults<UserLocationInfo> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalLocationInfo", UserLocationInfo.class);
            List<UserLocationInfo> list = outputType.getMappedResults();
            List<String> txtList = new ArrayList<>();
            for(UserLocationInfo userLocationInfo:list){
                txtList.add(userLocationInfo.toString());
            }

            String sourceFile ="D:\\Deskop\\智学互动\\定位次数\\source"+"_"+DateUtils.formatDate(start)+".txt";
            TxtUtil.write(txtList,sourceFile);
        }

//        String resultFile ="D:\\Deskop\\智学互动\\result.txt";
//        List<String> locationMap = TxtUtil.read(sourceFile);
//        List<HistoryImeiInfo> userInfos = prodUcMapper.getStudentBindInfo(null,1);
//        Map<Integer,HistoryImeiInfo> userMap = userInfos.stream().collect(Collectors.toMap(HistoryImeiInfo::getUserId, Function.identity(), (key1, key2) -> key2));
//        List<String> result = new ArrayList<>();
//        List<Integer> noCastUserId = new ArrayList<>();
//        for(String locationInfo:locationMap){
//            String[] arr = locationInfo.split(",");
//            Integer studentId = Integer.valueOf(arr[0]);
//            String locationNum = arr[1];
//            HistoryImeiInfo historyImeiInfo = userMap.get(studentId);
//            if(historyImeiInfo!=null){
//                String str = locationNum+","+historyImeiInfo.toString();
//                result.add(str);
//            }else {
//                noCastUserId.add(studentId);
//            }
//        }

    }

    /**
     * 全国用户定位次数匹配用户信息
     */
    @Test
    public void castLocationNum() throws IOException {
        String resultPath = "D:\\Deskop\\智学互动\\source.txt";
        String path = "D:\\Deskop\\智学互动\\定位次数";		//要遍历的路径
        File file = new File(path);		//获取其file对象
        File[] fs = file.listFiles();	//遍历path下的文件和目录，放在File数组中
        Map<Integer,UserLocationDetailInfo> userMap = new HashMap<>();
        List<HistoryImeiInfo> userInfos = prodUcMapper.getStudentBindInfo(null,1);
        Map<Integer,HistoryImeiInfo> userInfoMap = userInfos.stream().collect(Collectors.toMap(HistoryImeiInfo::getUserId, Function.identity(), (key1, key2) -> key2));
        Set<Integer> notCastUserId = new HashSet<>();
        for(File f:fs){				//遍历File[]数组
            if(!f.isDirectory()){
                System.out.println(f.getAbsoluteFile());
                List<String> txt = TxtUtil.read(f.getAbsolutePath());
                for(String str:txt){
                    String[] arr = str.split(",");
                    Integer userId = Integer.valueOf(arr[0]) ;
                    Integer locationNum = Integer.valueOf(arr[2]);
                    UserLocationDetailInfo locationInfo = userMap.get(userId);
                    HistoryImeiInfo userInfo = userInfoMap.get(userId);
                    if(userInfo==null){
                        notCastUserId.add(userId);
                        continue;
                    }
                    if(locationInfo==null){
                        locationInfo  = new UserLocationDetailInfo();
                        locationInfo.setUserId(userId);
                        locationInfo.setLocationNum(locationNum);
                        locationInfo.setProvince(userInfo.getProvinceName());
                        locationInfo.setCity(userInfo.getCityName());
                        locationInfo.setImei(userInfo.getImei());
                        locationInfo.setImeiPhone(userInfo.getImeiPhone());
                        locationInfo.setStudentName(userInfo.getUserName());
                        locationInfo.setParentTel(userInfo.getParentTel());
                        userMap.put(userId,locationInfo);
                    }else {
                        locationInfo.setLocationNum(locationNum+locationInfo.getLocationNum());
                        locationInfo.setProvince(userInfo.getProvinceName());
                        locationInfo.setCity(userInfo.getCityName());
                        locationInfo.setImei(userInfo.getImei());
                        locationInfo.setImeiPhone(userInfo.getImeiPhone());
                        locationInfo.setStudentName(userInfo.getUserName());
                        locationInfo.setParentTel(userInfo.getParentTel());
                        userMap.put(userId,locationInfo);
                    }
                }
            }
        }
        for(Integer studentId:notCastUserId){
            System.out.print(studentId+",");
        }
        List<String> list = new ArrayList<>();
        for(UserLocationDetailInfo userInfo:userMap.values()){
            list.add(userInfo.toString());
        }
        TxtUtil.write(list,resultPath);
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
