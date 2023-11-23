package com.qtone.util;

import com.alibaba.fastjson2.JSONObject;
import com.mongodb.client.result.UpdateResult;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dto.TerminalCallInfo;
import com.qtone.util.dto.TerminalLocationInfo;
import com.qtone.util.dto.TerminalPhone;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SaveJobCardLocationInfo {

    @Resource(name = "gpsMongoTemplate")
    MongoTemplate gpsMongoTemplate;
    @Resource
    ProdUcMapper prodUcMapper;
    @Resource(name = "jobCardMongoTemplate")
    MongoTemplate jobCardMongoTemplate;
    @Resource(name = "seniorCardMongoTemplate")
    MongoTemplate seniorCardMongoTemplate;


    @Test
    public void saveLocationInfoData() throws Exception {
//        Integer[] schoolIds = {10078667, 10079608, 10076391, 10047194};
        Integer[] schoolIds = {10078667};
        List<TerminalPhone> terminalPhones = prodUcMapper.getTerminalPhoneListBySchoolId(schoolIds);
        Map<Integer, TerminalPhone> userIdMap = terminalPhones.stream().collect(Collectors.toMap(TerminalPhone::getUserId, account -> account));

        for(Integer userId : userIdMap.keySet()) {
            TerminalPhone userInfo = userIdMap.get(userId);
            Query query2 = new Query();
            Criteria criteria2 = new Criteria();
            criteria2.and("userId").is(userId);
            query2.addCriteria(criteria2);
            Update update = new Update();
            update.set("userId", userInfo.getId());
            UpdateResult result = jobCardMongoTemplate.updateMulti(query2,update,TerminalLocationInfo.class,"terminalLocationInfo");
                    if(result.getMatchedCount()>0){
                        System.out.println(userId+":"+userInfo.getId());
                    }
        }

//
//        String startDate = "2023-08-15 00:00:00";
//        String endDate = "2023-08-31 00:00:00";
//        Date start = DateUtils.getDate(startDate);
//        Date end = DateUtils.getDate(endDate);
//        int betweenDate = DateUtils.daysBetweenDate(start, end);
//        for (int i = 0; i < betweenDate; i++) {
//            end = DateUtils.addDate(end, -i);
//            start = DateUtils.addDate(end, -1);
//            String startStr = DateUtils.formatDateTime(start);
//            String newEndStr = DateUtils.formatDateTime(end);
//            for (Integer schoolId : schoolIds) {
//                Query query = new Query();
//                Criteria criteria = new Criteria();
//                criteria.and("schoolId").is(schoolId);
//                criteria.and("locationTime").gte(startStr).lt(newEndStr);
//                query.addCriteria(criteria);
//                List<TerminalLocationInfo> gpsLocationInfo = gpsMongoTemplate.find(query, TerminalLocationInfo.class);
//
//                List<TerminalLocationInfo> addData = new ArrayList<>();
//                for (TerminalLocationInfo locationInfo : gpsLocationInfo) {
//                    TerminalPhone userInfo = userIdMap.get(locationInfo.getUserId());
//                    if (userInfo != null) {
//                        locationInfo.setUserId(userInfo.getId());
//                        locationInfo.setUserType(userInfo.getUserType());
//                        addData.add(locationInfo);
//                    }
//                }
//                jobCardMongoTemplate.insert(addData, "terminalLocationInfo");
//            }
//            System.out.println(newEndStr);
//        }
    }

    @Test
    public void saveLocationInfoData3() throws Exception {
        Integer[] schoolIds = {10070339};
        List<TerminalPhone> terminalPhones = prodUcMapper.getTerminalPhoneListBySchoolId(schoolIds);
        Map<Integer, TerminalPhone> userIdMap = terminalPhones.stream().collect(Collectors.toMap(TerminalPhone::getUserId, account -> account));

//        String startDate = "2023-08-15 00:00:00";
//        String endDate = "2023-08-31 00:00:00";
//        Date start = DateUtils.getDate(startDate);
//        Date end = DateUtils.getDate(endDate);
//        int betweenDate = DateUtils.daysBetweenDate(start, end);
//        for (int i = 0; i < betweenDate; i++) {
//            end = DateUtils.addDate(end, -i);
//            start = DateUtils.addDate(end, -1);
//            String startStr = DateUtils.formatDateTime(start);
//            String newEndStr = DateUtils.formatDateTime(end);
//            for (Integer schoolId : schoolIds) {
//                if(newEndStr.equals("2023-08-31 00:00:00") && schoolId.equals(10079608)){
//                    continue;
//                }
                Query query = new Query();
                Criteria criteria = new Criteria();
                criteria.and("schoolId").in(schoolIds);
//                criteria.and("locationTime").gte(startStr).lt(newEndStr);
                query.addCriteria(criteria);
                List<TerminalLocationInfo> gpsLocationInfo = gpsMongoTemplate.find(query, TerminalLocationInfo.class);

                for (TerminalLocationInfo locationInfo : gpsLocationInfo) {
                    TerminalPhone userInfo = userIdMap.get(locationInfo.getUserId());
                    if (userInfo != null) {
                        locationInfo.setUserId(userInfo.getId());
                        locationInfo.setUserType(userInfo.getUserType());

                    }
                }

        try {
//            jobCardMongoTemplate.remove(query,"terminalLocationInfo");
            jobCardMongoTemplate.insert(gpsLocationInfo, "terminalLocationInfo");
        } catch (Exception e) {
            e.printStackTrace();
        }
//            }
//            System.out.println(newEndStr);
//        }
    }

    @Test
    public void saveLocationInfoData2() throws Exception {
        Integer[] schoolIds = {10070339};
//        Integer[] schoolIds = {10047194};
        List<TerminalPhone> terminalPhones = prodUcMapper.getTerminalPhoneListBySchoolId(schoolIds);
        Map<Integer, TerminalPhone> userIdMap = terminalPhones.stream().collect(Collectors.toMap(TerminalPhone::getUserId, account -> account));

        String startDate = "2023-08-15 00:00:00";
        String endDate = "2023-08-30 00:00:00";
        Date start = DateUtils.getDate(startDate);
        Date end = DateUtils.getDate(endDate);
        int betweenDate = DateUtils.daysBetweenDate(start, end);
        for (int i = 0; i < betweenDate; i++) {
            start = DateUtils.addDate(end, -(betweenDate - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startStr = DateUtils.formatDateTime(start);
            String newEndStr = DateUtils.formatDateTime(newEnd);
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("schoolId").in(schoolIds);
            criteria.and("locationTime").gte(startStr).lt(newEndStr);
            query.addCriteria(criteria);
            List<TerminalLocationInfo> gpsLocationInfo = seniorCardMongoTemplate.find(query, TerminalLocationInfo.class, "terminalLocationInfo2");

            List<TerminalLocationInfo> addData = new ArrayList<>();
            for (TerminalLocationInfo locationInfo : gpsLocationInfo) {
                TerminalPhone userInfo = userIdMap.get(locationInfo.getUserId());
                if (userInfo != null) {
                    locationInfo.setUserId(userInfo.getId());
                    locationInfo.setUserType(userInfo.getUserType());
                    addData.add(locationInfo);
                }
            }
            jobCardMongoTemplate.remove(addData, "terminalLocationInfo");

            System.out.println("2222");
        }
    }


    @Test
    public void saveCallInfoData() throws Exception {
        Integer[] schoolIds = {10080647};

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("schoolId").in(schoolIds);
        query.addCriteria(criteria);
        List<TerminalCallInfo> gpsCallInfo = gpsMongoTemplate.find(query, TerminalCallInfo.class);

        jobCardMongoTemplate.insert(gpsCallInfo, "terminalCallInfo");
    }
}
