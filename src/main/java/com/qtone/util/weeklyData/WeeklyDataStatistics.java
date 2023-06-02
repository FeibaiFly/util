package com.qtone.util.weeklyData;

import com.qtone.util.DateUtils;
import com.qtone.util.StringUtils;
import com.qtone.util.dao.prod.ProdUcMapper;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 周报数据统计相关
 *
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 18:10 2023/6/1
 * @Modified By:
 */
@Controller
public class WeeklyDataStatistics {
    @Resource(name = "analysisMongoTemplate")
    private MongoTemplate analysisMongoTemplate;

    @Resource(name = "gpsMongoTemplate")
    private MongoTemplate gpsMongoTemplate;

    @Resource(name = "middlewareMongoTemplate")
    private MongoTemplate middlewareMongoTemplate;

    @Resource(name = "attendanceMongoTemplate")
    private MongoTemplate attendanceMongoTemplate;
    @Resource
    ProdUcMapper prodUcMapper;

    /**
     * 统计全国每月数据
     * （当月绑卡数
     * 当月无感考勤效
     * 当月拨打电话次数
     * 当月通话时长(分钟)
     * 当月安全定位次数
     * 当月家长主动发起定位次数
     * 当月课堂互动次数
     * 活跃率）
     *
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/1 18:13
     */
    @RequestMapping(value = "/statistics/data1", method = RequestMethod.POST)
    @ResponseBody
    public void statisticsData1(@RequestBody CardOrderRequest request) {
        if (!request.getAppKey().equals("cmcc@2021")) {
            return ;
        }
        Date start = DateUtils.getDate(request.getStartTime());
        Date end = DateUtils.getDate(request.getEndTime());
        getAddBindNum(start,end);
        getKqNum(start,end);
        getUserCallInfo(start,end);
        getUserLocationNum(start,end);
        getAnswerNum(start,end);
        getLocationNum(start,end);

    }

    //日期范围内新增绑卡数
    private Integer getAddBindNum(Date start, Date end) {
        Integer addBindNum = prodUcMapper.getAddBindNum(DateUtils.formatDateTime(start),DateUtils.formatDateTime(end));
        System.out.println("全国新增绑卡数："+addBindNum);
        return addBindNum;
    }

    //全国无感考勤次数
    private Integer getKqNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        Integer kqNum =0;
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);

            String date = DateUtils.formatDate(start);
            Criteria criteria = new Criteria();
            criteria.and("day").is(date);
            Query query = new Query();
            query.addCriteria(criteria);
            Long dayNum = attendanceMongoTemplate.count(query, "kqWorkCheckInStudentRecord");
            kqNum = kqNum+ dayNum.intValue();
        }
        System.out.println("全国无感考勤次数:"+kqNum);
        return kqNum;
    }

    // 获取用户通话次数，通话时间
    private Map<String,Integer> getUserCallInfo(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        Integer totalCallNum=0;
        Integer totalCallDuration=0;
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));

            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$callNum").as("callNum")
                    .and("$callDuration").as("callDuration");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("schoolId")
                            .count().as("callNum")
                            .sum("duration").as("callDuration")
                            .last("userId").as("userId"),
                    projectionOperation
            );
            AggregationResults<Map> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalCallInfo", Map.class);
            List<Map> list = outputType.getMappedResults();
            for(Map map:list){
                int callNum = StringUtils.objToInt(map.get("callNum")) ;
                int callDuration = StringUtils.objToInt(map.get("callDuration"));
                if(callNum>0){
                    totalCallNum=totalCallNum+callNum;
                }
                if(callDuration>0){
                    totalCallDuration=totalCallDuration+callDuration;
                }
            }
        }
        totalCallDuration = totalCallDuration/60;
        System.out.println("通话次数："+totalCallNum);
        System.out.println("通话时长："+totalCallDuration);
        Map<String,Integer> callInfoMap = new HashMap<>();
        callInfoMap.put("totalCallNum",totalCallNum);
        callInfoMap.put("totalCallDuration",totalCallDuration);
        return callInfoMap;
    }

    //获取用户安全定位次数(查询一个月数据太慢，只查七天的，在乘四)
    private Integer getLocationNum(Date start, Date end){
        int day = DateUtils.daysBetweenDate(start, end);
        int index=0;
        Integer locationNum =0;
        for (int i = 0; i < day; i++) {
            if(i<10){
                continue;
            }
            index++;
            if(index>7){
                break;
            }
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
            Query query = new Query();
            query.addCriteria(criteria);
            Long dayNum = gpsMongoTemplate.count(query, "terminalLocationInfo");
            locationNum = locationNum+ dayNum.intValue();
        }
        locationNum = locationNum*4;
        System.out.println("全国用户安全定位次数:"+locationNum);
        return locationNum;
    }

    //获取用户主动定位次数
    private Integer getUserLocationNum(Date start, Date end){
        int day = DateUtils.daysBetweenDate(start, end);
        Integer locationNum =0;
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
//            criteria.and("createTime").gte(DateUtils.formatDateTime(start)).lt(DateUtils.formatDateTime(end));
            Query query = new Query();
            query.addCriteria(criteria);
            Long dayNum = gpsMongoTemplate.count(query, "userPositioning");
            locationNum = locationNum+ dayNum.intValue();
        }
        System.out.println("全国用户主动定位次数:"+locationNum);
        return locationNum;
    }

    //获取互动课堂答题次数
    private Integer getAnswerNum(Date start, Date end){
        int answerNum = prodUcMapper.getAnswerNum(DateUtils.formatDateTime(start),DateUtils.formatDateTime(end));
        System.out.println("当月课堂互动次数:"+answerNum);
        return answerNum;
    }
}
