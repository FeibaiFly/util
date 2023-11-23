package com.qtone.util.saveSeniorCardData;

import com.qtone.util.DateUtils;
import com.qtone.util.StringUtils;
import com.qtone.util.saveSeniorCardData.dto.TerminalLocationBound;
import com.qtone.util.saveSeniorCardData.dto.TerminalLocationInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 15:15 2023/8/9
 * @Modified By:
 */
@Service
public class SaveJobCardData {
    @Resource(name = "gpsMongoTemplate")
    private MongoTemplate gpsMongoTemplate;
    @Resource(name = "jobCardMongoTemplate")
    private MongoTemplate jocardMongoTemplate;


    public void saveLocationInfo() {
        Query query2 = new Query(new Criteria().and("schoolId").is(400043778));
        jocardMongoTemplate.remove(query2, "terminalLocationInfo");

        Integer[] gpsUserIds = {3543271, 3614568, 3637517, 3406398, 3166439, 3472881, 3735415, 3442818, 3421058, 3428278};
        String startTime = "2023-08-08 00:00:00";
        String endTime = "2023-08-11 00:00:00";
        String[] userInfos = {
                "2821,860000111100020,康煜",
                "2822,860000111100019,魏东平",
                "2823,860000111100018,张开平",
                "2824,860000111100017,李江河",
                "2825,860000111100016,赵漠然",
                "2826,860000111100015,洪河",
                "2827,860000111100014,王云堂",
                "2828,860000111100013,李君",
                "2829,860000111100012,张亚平",
                "2830,860000111100011,李伟"};
        List<TerminalLocationInfo> seniorDatas = new ArrayList<>();
        for (int i = 0; i < gpsUserIds.length; i++) {
            Integer gpsUserId = gpsUserIds[i];
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.and("userId").is(gpsUserId).and("locationTime").gte(startTime).lte(endTime);
            query.addCriteria(criteria);
            query.with(Sort.by(Sort.Order.asc("locationTime")));
            List<TerminalLocationInfo> gpsLocationInfo = gpsMongoTemplate.find(query, TerminalLocationInfo.class);
            String userInfo = userInfos[i];
            String[] arr = userInfo.split(",");
            for (TerminalLocationInfo terminalLocationInfo : gpsLocationInfo) {
                TerminalLocationInfo newData = new TerminalLocationInfo();
                BeanUtils.copyProperties(terminalLocationInfo, newData);
                newData.setId(null);
                newData.setCreateTime(DateUtils.formatDateTime(DateUtils.addDate(DateUtils.getDate(terminalLocationInfo.getCreateTime(), DateUtils.DAFAULT_DATETIME_FORMAT), 1)));
                newData.setLocationTime(DateUtils.formatDateTime(DateUtils.addDate(DateUtils.getDate(terminalLocationInfo.getLocationTime(), DateUtils.DAFAULT_DATETIME_FORMAT), 1)));
                newData.setUserId(StringUtils.objToInt(arr[0]));
                newData.setImei(arr[1]);
                newData.setUserName(arr[2]);
                newData.setSchoolId(400043778);
                newData.setUserTel(null);
                newData.setCardNum(null);
                seniorDatas.add(newData);

            }
        }
        jocardMongoTemplate.insert(seniorDatas, "terminalLocationInfo");
        System.out.println("======");
    }

    public void saveLocationBound() {
        Query query2 = new Query(new Criteria().and("schoolId").is(400043778));
        jocardMongoTemplate.remove(query2, "terminalLocationBound");

        Integer gpsUserId = 1194148;
        String startTime = "2023-08-08 00:00:00";
        String endTime = "2023-08-09 00:00:00";
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("userId").is(gpsUserId).and("locationTime").gte(startTime).lte(endTime);
        query.addCriteria(criteria);
        List<TerminalLocationBound> gpsLocationInfo = gpsMongoTemplate.find(query, TerminalLocationBound.class);
        String[] userInfos = {
                "2821,860000111100020,康煜",
                "2822,860000111100019,魏东平",
                "2823,860000111100018,张开平",
                "2824,860000111100017,李江河",
                "2825,860000111100016,赵漠然",
                "2826,860000111100015,洪河",
                "2827,860000111100014,王云堂",
                "2828,860000111100013,李君",
                "2829,860000111100012,张亚平",
                "2830,860000111100011,李伟"};
        List<TerminalLocationBound> seniorDatas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (TerminalLocationBound terminalLocationInfo : gpsLocationInfo) {
                for (String userInfo : userInfos) {
                    TerminalLocationBound newData = new TerminalLocationBound();
                    BeanUtils.copyProperties(terminalLocationInfo, newData);
                    newData.setId(null);
                    newData.setCreateTime(DateUtils.formatDateTime(DateUtils.addDate(DateUtils.getDate(terminalLocationInfo.getCreateTime(), DateUtils.DAFAULT_DATETIME_FORMAT), i + 1)));
                    newData.setLocationTime(DateUtils.formatDateTime(DateUtils.addDate(DateUtils.getDate(terminalLocationInfo.getLocationTime(), DateUtils.DAFAULT_DATETIME_FORMAT), i + 1)));

                    String[] arr = userInfo.split(",");
                    newData.setUserId(StringUtils.objToInt(arr[0]));
                    newData.setImei(arr[1]);
                    newData.setUserName(arr[2]);
                    newData.setSchoolId(400043778);
                    newData.setUserTel(null);
                    newData.setCardNum(null);
                    seniorDatas.add(newData);
                }
            }
        }
        jocardMongoTemplate.insert(seniorDatas, "terminalLocationBound");
        System.out.println("======");
    }
}
