package com.qtone.util.weeklyData;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qtone.util.DateUtils;
import com.qtone.util.PoiUtil;
import com.qtone.util.StringUtils;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dao.test.SchoolWeekDataStatisticsMapper;
import com.qtone.util.dao.test.TestUcMapper;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

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
    @Resource
    SchoolWeekDataStatisticsMapper schoolWeekDataStatisticsMapper;

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
            return;
        }
        Date start = DateUtils.getDate(request.getStartTime());
        Date end = DateUtils.getDate(request.getEndTime());
        getAddBindNum(start, end);
        getKqNum(start, end);
        getUserCallInfo(start, end);
        getUserLocationNum(start, end);
        getAnswerNum(start, end);
        getLocationNum(start, end);

    }


    /**
     * 统计按学校分组的周报数据（家长请求定位次数、SOS告警发起次数、围栏触发报警次数、设备上报定位次数、通话次数、通话时长、新增学生数、绑卡数、解绑数）
     *
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/5 14:43
     */
    @RequestMapping(value = "/statistics/data2", method = RequestMethod.POST)
    @ResponseBody
    public void statisticsData2(HttpServletRequest htpRequest, HttpServletResponse response, @RequestBody CardOrderRequest request) throws Exception {
        if (!request.getAppKey().equals("cmcc@2021")) {
            return;
        }

//        getCallInfoHistory();
        Date start = DateUtils.getDate(request.getStartTime());
        Date end = DateUtils.getDate(request.getEndTime());

        List<SchoolWeekDataStatistics> schoolBindInfo = getSchoolBindInfo(start, end);
        List<SchoolWeekDataStatistics> schoolUnBindNum = getSchoolUnBindNum(start, end);
        List<SchoolWeekDataStatistics> schoolCallInfo = geSchoolCallInfo(start, end);
        List<SchoolWeekDataStatistics> schoolLocationNum = getSchoolLocationNum(start, end);
        List<SchoolWeekDataStatistics> schoolRequestLocationNum = getSchoolRequestLocationNum(start, end);
        List<SchoolWeekDataStatistics> schoolAlarmNum = getSchoolAlarmNum(start, end);
        for (SchoolWeekDataStatistics data : schoolCallInfo) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                SchoolWeekDataStatistics record = schoolBindInfo.get(index);
                record.setCallNum(record.getCallNum() + data.getCallNum());
                record.setCallDuration(record.getCallDuration() + data.getCallDuration());
            }
        }
        for (SchoolWeekDataStatistics data : schoolLocationNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                SchoolWeekDataStatistics record = schoolBindInfo.get(index);
                record.setReportLocationNum(record.getReportLocationNum() + data.getReportLocationNum());
            }
        }

        for (SchoolWeekDataStatistics data : schoolRequestLocationNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                SchoolWeekDataStatistics record = schoolBindInfo.get(index);
                record.setRequestLocationNum(record.getRequestLocationNum() + data.getRequestLocationNum());
            }
        }

        for (SchoolWeekDataStatistics data : schoolAlarmNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                SchoolWeekDataStatistics record = schoolBindInfo.get(index);
                record.setSosAlarmNum(record.getSosAlarmNum() + data.getSosAlarmNum());
                record.setRegionalAlarmNum(record.getRegionalAlarmNum() + data.getRegionalAlarmNum());
            }
        }
        for (SchoolWeekDataStatistics data : schoolUnBindNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                SchoolWeekDataStatistics record = schoolBindInfo.get(index);
                record.setUnBindNum(data.getUnBindNum());
            }
        }

        for(SchoolWeekDataStatistics data:schoolBindInfo){
            SchoolWeekDataStatistics oldInfo = schoolWeekDataStatisticsMapper.getBySchoolId(data.getSchoolId());
            if(oldInfo!=null){
                data.setId(oldInfo.getId());
                data.setTotalCallDuration(oldInfo.getTotalCallDuration()+data.getCallDuration());
                data.setTotalCallNum(oldInfo.getTotalCallNum()+data.getCallNum());
                schoolWeekDataStatisticsMapper.updateByPrimaryKey(data);
            }else{
                data.setTotalCallDuration(data.getCallDuration());
                data.setTotalCallNum(data.getCallNum());
                schoolWeekDataStatisticsMapper.insertData(data);
            }
        }
        String[] header = {"省", "市", "区", "学校ID", "学校名称", "总学生数", "新增学生数", "总绑卡数", "新增绑卡数","总通话次数", "新增通话次数",
                "总通话时长","新增通话时长", "新增设备上报定位次数", "新增家长请求定位次数", "新增SOS告警发起次数", "新增围栏触发报警次数", "新增解绑数"};
        String fileName = "周报数据";


        PoiUtil.exportExcel(fileName, fileName, header, schoolBindInfo, htpRequest, response);
    }


    //全国日期范围内新增绑卡数
    private Integer getAddBindNum(Date start, Date end) {
        Integer addBindNum = prodUcMapper.getAddBindNum(DateUtils.formatDateTime(start), DateUtils.formatDateTime(end));
        System.out.println("全国新增绑卡数：" + addBindNum);
        return addBindNum;
    }

    //全国无感考勤次数
    private Integer getKqNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        Integer kqNum = 0;
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);

            String date = DateUtils.formatDate(start);
            Criteria criteria = new Criteria();
            criteria.and("day").is(date);
            Query query = new Query();
            query.addCriteria(criteria);
            Long dayNum = attendanceMongoTemplate.count(query, "kqWorkCheckInStudentRecord");
            kqNum = kqNum + dayNum.intValue();
        }
        System.out.println("全国无感考勤次数:" + kqNum);
        return kqNum;
    }

    //获取全国用户通话次数，通话时间
    private Map<String, Integer> getUserCallInfo(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        Integer totalCallNum = 0;
        Integer totalCallDuration = 0;
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
            for (Map map : list) {
                int callNum = StringUtils.objToInt(map.get("callNum"));
                int callDuration = StringUtils.objToInt(map.get("callDuration"));
                if (callNum > 0) {
                    totalCallNum = totalCallNum + callNum;
                }
                if (callDuration > 0) {
                    totalCallDuration = totalCallDuration + callDuration;
                }
            }
        }
        totalCallDuration = totalCallDuration / 60;
        System.out.println("通话次数：" + totalCallNum);
        System.out.println("通话时长：" + totalCallDuration);
        Map<String, Integer> callInfoMap = new HashMap<>();
        callInfoMap.put("totalCallNum", totalCallNum);
        callInfoMap.put("totalCallDuration", totalCallDuration);
        return callInfoMap;
    }

    //获取全国用户安全定位次数(查询一个月数据太慢，只查七天的，在乘四)
    private Integer getLocationNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        int index = 0;
        Integer locationNum = 0;
        for (int i = 0; i < day; i++) {
            if (i < 10) {
                continue;
            }
            index++;
            if (index > 7) {
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
            locationNum = locationNum + dayNum.intValue();
        }
        locationNum = locationNum * 4;
        System.out.println("全国用户安全定位次数:" + locationNum);
        return locationNum;
    }

    //获取全国用户主动定位次数
    private Integer getUserLocationNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        Integer locationNum = 0;
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
            locationNum = locationNum + dayNum.intValue();
        }
        System.out.println("全国用户主动定位次数:" + locationNum);
        return locationNum;
    }

    //获取全国互动课堂答题次数
    private Integer getAnswerNum(Date start, Date end) {
        int answerNum = prodUcMapper.getAnswerNum(DateUtils.formatDateTime(start), DateUtils.formatDateTime(end));
        System.out.println("当月课堂互动次数:" + answerNum);
        return answerNum;
    }

    //日期范围内学校全部和新增绑卡数，学生数(只查询有绑卡学生的学校)
    private List<SchoolWeekDataStatistics> getSchoolBindInfo(Date start, Date end) {
        List<SchoolWeekDataStatistics> record = prodUcMapper.getSchoolBindInfo(DateUtils.formatDateTime(start), DateUtils.formatDateTime(end));
        return record;
    }

    //获取学校用户通话次数，通话时间
    private List<SchoolWeekDataStatistics> geSchoolCallInfo(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        List<SchoolWeekDataStatistics> records = new ArrayList<>();
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));

            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$schoolId").as("schoolId")
                    .and("$callNum").as("callNum")
                    .and("$callDuration").as("callDuration");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("schoolId")
                            .count().as("callNum")
                            .sum("duration").as("callDuration")
                            .last("schoolId").as("schoolId"),
                    projectionOperation
            );
            AggregationResults<SchoolWeekDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalCallInfo", SchoolWeekDataStatistics.class);
            List<SchoolWeekDataStatistics> list = outputType.getMappedResults();
            for (SchoolWeekDataStatistics data : list) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    SchoolWeekDataStatistics record = records.get(index);
                    record.setCallNum(record.getCallNum() + data.getCallNum());
                    record.setCallDuration(record.getCallDuration() + data.getCallDuration());
                } else {
                    records.add(data);
                }
            }
        }
        return records;
    }

    //获取学校用户安全定位次数
    private List<SchoolWeekDataStatistics> getSchoolLocationNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        List<SchoolWeekDataStatistics> records = new ArrayList<>();
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
            Query query = new Query();
            query.addCriteria(criteria);
            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$schoolId").as("schoolId")
                    .and("$reportLocationNum").as("reportLocationNum");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("schoolId")
                            .count().as("reportLocationNum")
                            .last("schoolId").as("schoolId"),
                    projectionOperation
            );
            AggregationResults<SchoolWeekDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalLocationInfo", SchoolWeekDataStatistics.class);
            List<SchoolWeekDataStatistics> list = outputType.getMappedResults();
            for (SchoolWeekDataStatistics data : list) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    SchoolWeekDataStatistics record = records.get(index);
                    record.setReportLocationNum(record.getReportLocationNum() + data.getReportLocationNum());
                } else {
                    records.add(data);
                }
            }
        }
        return records;
    }

    //获取学校用户主动定位次数
    private List<SchoolWeekDataStatistics> getSchoolRequestLocationNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        List<SchoolWeekDataStatistics> records = new ArrayList<>();
        Integer locationNum = 0;
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
            locationNum = locationNum + dayNum.intValue();
            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$schoolId").as("schoolId")
                    .and("$requestLocationNum").as("requestLocationNum");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("schoolId")
                            .count().as("requestLocationNum")
                            .last("schoolId").as("schoolId"),
                    projectionOperation
            );
            AggregationResults<SchoolWeekDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "userPositioning", SchoolWeekDataStatistics.class);
            List<SchoolWeekDataStatistics> list = outputType.getMappedResults();
            for (SchoolWeekDataStatistics data : list) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    SchoolWeekDataStatistics record = records.get(index);
                    record.setRequestLocationNum(record.getRequestLocationNum() + data.getRequestLocationNum());
                } else {
                    records.add(data);
                }
            }
        }
        return records;
    }


    //获取学校sos、围栏越界告警
    private List<SchoolWeekDataStatistics> getSchoolAlarmNum(Date start, Date end) {
        int day = DateUtils.daysBetweenDate(start, end);
        List<SchoolWeekDataStatistics> records = new ArrayList<>();
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            //sos告警统计
            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
            criteria.and("alarmType").is(1);
            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$schoolId").as("schoolId")
                    .and("$sosAlarmNum").as("sosAlarmNum");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("schoolId")
                            .count().as("sosAlarmNum")
                            .last("schoolId").as("schoolId"),
                    projectionOperation
            );
            AggregationResults<SchoolWeekDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalLocationBound", SchoolWeekDataStatistics.class);
            List<SchoolWeekDataStatistics> list = outputType.getMappedResults();
            for (SchoolWeekDataStatistics data : list) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    SchoolWeekDataStatistics record = records.get(index);
                    record.setSosAlarmNum(record.getSosAlarmNum() + data.getSosAlarmNum());
                } else {
                    records.add(data);
                }
            }

            //围栏越界告警统计
            Criteria criteria2 = new Criteria();
            criteria2.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
            criteria2.and("alarmType").is(2);
            // 返回字段
            ProjectionOperation projectionOperation2 = Aggregation.project()
                    .and("$schoolId").as("schoolId")
                    .and("$regionalAlarmNum").as("regionalAlarmNum");
            // 聚合查询
            Aggregation aggregation2 = Aggregation.newAggregation(
                    Aggregation.match(criteria2),
                    Aggregation.group("schoolId")
                            .count().as("regionalAlarmNum")
                            .last("schoolId").as("schoolId"),
                    projectionOperation2
            );
            AggregationResults<SchoolWeekDataStatistics> outputType2 = gpsMongoTemplate.aggregate(aggregation2, "terminalLocationBound", SchoolWeekDataStatistics.class);
            List<SchoolWeekDataStatistics> list2 = outputType2.getMappedResults();
            for (SchoolWeekDataStatistics data : list2) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    SchoolWeekDataStatistics record = records.get(index);
                    record.setRegionalAlarmNum(record.getRegionalAlarmNum() + data.getRegionalAlarmNum());
                } else {
                    records.add(data);
                }
            }
        }
        return records;
    }


    //获取学校解绑数
    private List<SchoolWeekDataStatistics> getSchoolUnBindNum(Date start, Date end) {
        List<SchoolWeekDataStatistics> records = new ArrayList<>();
        String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
        String endTime = String.format("%08x", end.getTime() / 1000) + "0000000000000000";

        Criteria criteria = new Criteria();
        criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria.and("businessType").is(4);
        Query query = new Query();
        query.addCriteria(criteria);
        List<String> ids = middlewareMongoTemplate.findDistinct(query, "operatedUserId", "operLog", String.class);
        List<Integer> studentId = new ArrayList<>();
        for (String id : ids) {
            studentId.add(StringUtils.objToInt(id));
        }
        if (studentId.size() > 0) {
            records = prodUcMapper.getSchoolUnBindNumByStudetnId(studentId);
        }
        return records;
    }


    //统计去年20221010至20230529数据
    private void getCallInfoHistory() {
        Date start = DateUtils.getDate(20221010);
        Date end = DateUtils.getDate(20230529);
        int day = DateUtils.daysBetweenDate(start, end);
        List<SchoolWeekDataStatistics> records = new ArrayList<>();
        for (int i = 0; i < day; i++) {
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));

            // 返回字段
            ProjectionOperation projectionOperation = Aggregation.project()
                    .and("$schoolId").as("schoolId")
                    .and("$callNum").as("callNum")
                    .and("$callDuration").as("callDuration");
            // 聚合查询
            Aggregation aggregation = Aggregation.newAggregation(
                    Aggregation.match(criteria),
                    Aggregation.group("schoolId")
                            .count().as("callNum")
                            .sum("duration").as("callDuration")
                            .last("schoolId").as("schoolId"),
                    projectionOperation
            );
            AggregationResults<SchoolWeekDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalCallInfo", SchoolWeekDataStatistics.class);
            List<SchoolWeekDataStatistics> list = outputType.getMappedResults();
            for (SchoolWeekDataStatistics data : list) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    SchoolWeekDataStatistics record = records.get(index);
                    record.setCallNum(record.getCallNum() + data.getCallNum());
                    record.setCallDuration(record.getCallDuration() + data.getCallDuration());
                } else {
                    records.add(data);
                }
            }
        }
        for (SchoolWeekDataStatistics data : records) {
            schoolWeekDataStatisticsMapper.incrTotalCallInfo(data.getSchoolId(),data.getCallNum(),data.getCallDuration());
        }
    }
}
