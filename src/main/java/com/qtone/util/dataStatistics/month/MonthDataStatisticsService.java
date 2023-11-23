package com.qtone.util.dataStatistics.month;

import com.qtone.util.DateUtils;
import com.qtone.util.MpAesUtilProd;
import com.qtone.util.PoiUtil;
import com.qtone.util.StringUtils;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dao.test.SchoolMonthDataStatisticsMapper;
import com.qtone.util.dao.test.SchoolWeekDataStatisticsMapper;
import com.qtone.util.dataStatistics.CardOrderRequest;
import com.qtone.util.dataStatistics.month.dto.GuangDongDataStatistics;
import com.qtone.util.dataStatistics.month.dto.SchoolUserDto;
import com.qtone.util.dataStatistics.month.dto.StudentCardInfo;
import com.qtone.util.dataStatistics.week.dto.SchoolWeekDataStatistics;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 月报数据统计相关
 *
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 18:10 2023/6/1
 * @Modified By:
 */
@Slf4j
@Service
public class MonthDataStatisticsService {

    @Resource(name = "gpsMongoTemplate")
    private MongoTemplate gpsMongoTemplate;

    @Resource(name = "middlewareMongoTemplate")
    private MongoTemplate middlewareMongoTemplate;

    @Resource
    ProdUcMapper prodUcMapper;
    @Resource
    SchoolWeekDataStatisticsMapper schoolWeekDataStatisticsMapper;
    @Resource
    SchoolMonthDataStatisticsMapper schoolMonthDataStatisticsMapper;


    /**
     * 统计广东省按地市分组的
     * 地市,平台总用户数,本月新增用户数,总绑卡数,本月新增绑卡数量,总通话次数,本月新增通话次数,本月通话用户数,总通话时长(m),
     * 本月新增通话时长(m),总家长请求次数,本月新增家长请求次数,本月使用家长请求定位的用户数,总SOS发起次数,本月新增SOS发起次数,
     * 本月发起SOS呼叫的用户数,总围栏报警次数,本月新增围栏报警次数,主动上报定位次数,本月解绑数,本月解绑率
     *
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/5 14:43
     */
    public void statisticsGuangdong(CardOrderRequest request) throws Exception {

        Date start = DateUtils.getDate(request.getStartTime());
        Date end = DateUtils.getDate(request.getEndTime());
        Integer provinceId = 2125;
        List<GuangDongDataStatistics> schoolBindInfo = getSchoolBindInfo(start, end, provinceId);
        List<Integer> schoolIds = schoolBindInfo.stream().map(a -> a.getSchoolId()).collect(Collectors.toList());

        List<GuangDongDataStatistics> schoolUnBindNum = getSchoolUnBindNum(start, end, schoolIds);
        List<GuangDongDataStatistics> schoolCallInfo = geSchoolCallInfo(start, end, schoolIds);
        List<GuangDongDataStatistics> schoolRequestLocationNum = getSchoolRequestLocationNum(start, end, schoolIds);
        List<GuangDongDataStatistics> schoolAlarmNum = getSchoolAlarmNum(start, end, schoolIds);
        List<GuangDongDataStatistics> schoolLocationNum = getSchoolLocationNum(start, end, schoolIds);


        //统计总通话时长和次数，只执行一次
        for (GuangDongDataStatistics data : schoolCallInfo) {
            getCallInfoHistory(data);
        }

        for (GuangDongDataStatistics data : schoolCallInfo) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                GuangDongDataStatistics record = schoolBindInfo.get(index);
                record.setCallNum(data.getCallNum());
                record.setCallDuration(data.getCallDuration());
                record.setCallUserNum(data.getCallUserNum());
                record.setTotalCallNum(record.getTotalCallNum() + data.getTotalCallNum());
                record.setTotalCallDuration(record.getTotalCallDuration() + data.getTotalCallDuration());
            }
        }
        for (GuangDongDataStatistics data : schoolLocationNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                GuangDongDataStatistics record = schoolBindInfo.get(index);
                record.setReportLocationNum(data.getReportLocationNum());
            }
        }

        for (GuangDongDataStatistics data : schoolRequestLocationNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                GuangDongDataStatistics record = schoolBindInfo.get(index);
                record.setRequestLocationNum(data.getRequestLocationNum());
                record.setRequestLocationUserNum(data.getRequestLocationUserNum());
                record.setTotalRequestLocationNum(data.getTotalRequestLocationNum());
            }
        }

        for (GuangDongDataStatistics data : schoolAlarmNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                GuangDongDataStatistics record = schoolBindInfo.get(index);
                record.setSosAlarmNum(data.getSosAlarmNum());
                record.setSosAlarmUserNum(data.getSosAlarmUserNum());
                record.setRegionalAlarmNum(data.getRegionalAlarmNum());
                record.setTotalSosAlarmNum(data.getTotalSosAlarmNum());
                record.setTotalRegionalAlarmNum(data.getTotalRegionalAlarmNum());
            }
        }
        for (GuangDongDataStatistics data : schoolUnBindNum) {
            int index = schoolBindInfo.indexOf(data);
            if (index >= 0) {
                GuangDongDataStatistics record = schoolBindInfo.get(index);
                record.setUnBindNum(data.getUnBindNum());
            }
        }
        Map<Integer, GuangDongDataStatistics> cityDataMap = new HashMap<>();
        for (GuangDongDataStatistics data : schoolBindInfo) {
            Integer cityId = data.getCityId();
            GuangDongDataStatistics cityData = cityDataMap.get(cityId);
            if (cityData == null) {
                data.setDistrictId(null);
                data.setDistrictName(null);
                cityDataMap.put(cityId, data);
            } else {
                cityData.setTotalStudentNum(cityData.getTotalStudentNum() + data.getTotalStudentNum());
                cityData.setStudentNum(cityData.getStudentNum() + data.getStudentNum());
                cityData.setTotalBindNum(cityData.getTotalBindNum() + data.getTotalBindNum());
                cityData.setBindNum(cityData.getBindNum() + data.getBindNum());
                cityData.setTotalCallNum(cityData.getTotalCallNum() + data.getTotalCallNum());
                cityData.setCallNum(cityData.getCallNum() + data.getCallNum());
                cityData.setCallUserNum(cityData.getCallUserNum() + data.getCallUserNum());
                cityData.setTotalCallDuration(cityData.getTotalCallDuration() + data.getTotalCallDuration());
                cityData.setCallDuration(cityData.getCallDuration() + data.getCallDuration());
                cityData.setTotalRequestLocationNum(cityData.getTotalRequestLocationNum() + data.getTotalRequestLocationNum());
                cityData.setRequestLocationNum(cityData.getRequestLocationNum() + data.getRequestLocationNum());
                cityData.setRequestLocationUserNum(cityData.getRequestLocationUserNum() + data.getRequestLocationUserNum());
                cityData.setTotalSosAlarmNum(cityData.getTotalSosAlarmNum() + data.getTotalSosAlarmNum());
                cityData.setSosAlarmNum(cityData.getSosAlarmNum() + data.getSosAlarmNum());
                cityData.setSosAlarmUserNum(cityData.getSosAlarmUserNum() + data.getSosAlarmUserNum());
                cityData.setTotalRegionalAlarmNum(cityData.getTotalRegionalAlarmNum() + data.getTotalRegionalAlarmNum());
                cityData.setRegionalAlarmNum(cityData.getRegionalAlarmNum() + data.getRegionalAlarmNum());
                cityData.setReportLocationNum(cityData.getReportLocationNum() + data.getReportLocationNum());
                cityData.setUnBindNum(cityData.getUnBindNum() + data.getUnBindNum());
            }
        }
        Integer day = DateUtils.getIntDate(start);
        Integer month = day / 100;
        Integer lastMonth = DateUtils.getIntDate(DateUtils.addDate(start, -20)) / 100;
        schoolMonthDataStatisticsMapper.deleteByMonth(month);
        for (Integer cityId : cityDataMap.keySet()) {
            GuangDongDataStatistics monthData = cityDataMap.get(cityId);
            monthData.setMonth(month);
            GuangDongDataStatistics lastMonthData = schoolMonthDataStatisticsMapper.getByCityIdMonth(lastMonth, cityId);
            if (lastMonthData != null) {
                monthData.setTotalRegionalAlarmNum(monthData.getTotalRegionalAlarmNum() + lastMonthData.getTotalRegionalAlarmNum());
                monthData.setTotalSosAlarmNum(monthData.getTotalSosAlarmNum() + lastMonthData.getTotalSosAlarmNum());
                monthData.setTotalRequestLocationNum(monthData.getTotalRequestLocationNum() + lastMonthData.getTotalRequestLocationNum());
                monthData.setTotalCallDuration(monthData.getTotalCallDuration() + lastMonthData.getTotalCallDuration());
                monthData.setTotalCallNum(monthData.getTotalCallNum() + lastMonthData.getTotalCallNum());
            }
            //计算解绑率
            if (monthData.getUnBindNum() > 0) {
                int bindNum = monthData.getTotalBindNum() + monthData.getUnBindNum();
                int unBindNum = monthData.getUnBindNum();
                BigDecimal b1 = new BigDecimal(bindNum);
                BigDecimal b2 = new BigDecimal(unBindNum);
                //保留四位小数

                String rate = b2.divide(b1).setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue() * 100 + "%";
                monthData.setBindRate(rate);
            }
            schoolMonthDataStatisticsMapper.insertData(monthData);
        }

        String[] header = {"地市","平台总用户数","本月新增用户数","总绑卡数","本月新增绑卡数量","总通话次数","本月新增通话次数","本月通话用户数",
                "总通话时长(m)","本月新增通话时长(m)","总家长请求次数","本月新增家长请求次数","本月使用家长请求定位的用户数","总SOS发起次数",
                "本月新增SOS发起次数","本月发起SOS呼叫的用户数","总围栏报警次数","本月新增围栏报警次数","主动上报定位次数","本月解绑数","本月解绑率"
        };
        String fileName = "广东月报数据";


        PoiUtil.createExcel(fileName, fileName, header, schoolBindInfo);


    }

    /**
     * 统计江西上饶市绑卡学生数据，目前只执行一次
     *
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/8 15:34
     */

    public void statisticsData2(CardOrderRequest request) throws Exception {

        Integer cityId = 1465;
        List<StudentCardInfo> list = prodUcMapper.getStudentCardInfoByCityId(cityId);
        List<Integer> userId = new ArrayList<>();
        for (StudentCardInfo data : list) {
            userId.add(data.getStudentId());
            if (StringUtils.isNotEmpty(data.getParentTel())) {
                String[] arr = data.getParentTel().split(",");
                String tels = "";
                for (String tel : arr) {
                    tels = tels + "," + MpAesUtilProd.decode(tel);
                }
                tels = tels.replaceFirst(",", "");
                tels = tels.replaceAll(",,", ",");
                data.setParentTel(tels);
            }

            if (StringUtils.isNotEmpty(data.getImeiPhone())) {
                String imeiPhone = MpAesUtilProd.decode(data.getImeiPhone());
                data.setImeiPhone(imeiPhone);
            }
        }

        Criteria criteria = new Criteria();
        criteria.and("userId").in(userId);
        criteria.and("userType").is(2);
        criteria.and("createTime").gte("2023-01-01 00:00:00").lt("2023-02-01 00:00:00");
        Query query = new Query();
        query.addCriteria(criteria);
        List<String> imei1List = gpsMongoTemplate.findDistinct(query, "imei", "terminalCallInfo", String.class);

        Criteria criteria2 = new Criteria();
        criteria2.and("userId").in(userId);
        criteria2.and("userType").is(2);
        criteria2.and("createTime").gte("2023-02-01 00:00:00").lt("2023-03-01 00:00:00");
        Query query2 = new Query();
        query2.addCriteria(criteria2);
        List<String> imei21List = gpsMongoTemplate.findDistinct(query2, "imei", "terminalCallInfo", String.class);

        Criteria criteria3 = new Criteria();
        criteria3.and("userId").in(userId);
        criteria3.and("userType").is(2);
        criteria3.and("createTime").gte("2023-03-01 00:00:00").lt("2023-04-01 00:00:00");
        Query query3 = new Query();
        query3.addCriteria(criteria3);
        List<String> imei3List = gpsMongoTemplate.findDistinct(query3, "imei", "terminalCallInfo", String.class);

        Criteria criteria4 = new Criteria();
        criteria4.and("userId").in(userId);
        criteria4.and("userType").is(2);
        criteria4.and("createTime").gte("2023-04-01 00:00:00").lt("2023-05-01 00:00:00");
        Query query4 = new Query();
        query4.addCriteria(criteria4);
        List<String> imei4List = gpsMongoTemplate.findDistinct(query4, "imei", "terminalCallInfo", String.class);

        Criteria criteria5 = new Criteria();
        criteria5.and("userId").in(userId);
        criteria5.and("userType").is(2);
        criteria5.and("createTime").gte("2023-05-01 00:00:00").lt("2023-06-01 00:00:00");
        Query query5 = new Query();
        query5.addCriteria(criteria5);
        List<String> imei5List = gpsMongoTemplate.findDistinct(query5, "imei", "terminalCallInfo", String.class);

        for(StudentCardInfo data : list){
            if(imei1List.contains(data.getImei())){
                data.setIs1Active("是");
            }
            if(imei21List.contains(data.getImei())){
                data.setIs2Active("是");
            }
            if(imei3List.contains(data.getImei())){
                data.setIs3Active("是");
            }
            if(imei4List.contains(data.getImei())){
                data.setIs4Active("是");
            }
            if(imei5List.contains(data.getImei())){
                data.setIs5Active("是");
            }
        }
        String[] header = {"省", "市", "区", "学校名称", "学生ID", "学生姓名", "家长姓名", "家长手机号","插卡号码", "发卡时间",
                "初次发卡时间","imei", "5月否活跃", "4月否活跃", "3月否活跃", "2月否活跃", "1月否活跃"};
        String fileName = "上饶绑卡学生信息";


        PoiUtil.createExcel(request.getFilePath(), fileName, header, list);

    }


    //日期范围内学校全部和新增绑卡数，学生数(只查询有绑卡学生的学校)
    private List<GuangDongDataStatistics> getSchoolBindInfo(Date start, Date end, Integer provinceId) {
        List<GuangDongDataStatistics> record = prodUcMapper.getSchoolBindInfoByProvinceId(DateUtils.formatDateTime(start), DateUtils.formatDateTime(end), provinceId);
        return record;
    }

    //获取学校用户通话次数，通话时间,通话用户数
    private List<GuangDongDataStatistics> geSchoolCallInfo(Date start, Date end, List<Integer> schoolIds) {
        log.info("获取学校用户通话次数，通话时间,通话用户数开始");
        List<GuangDongDataStatistics> records = new ArrayList<>();
        String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
        String endTime = String.format("%08x", end.getTime() / 1000) + "0000000000000000";

        Criteria criteria = new Criteria();
        criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria.and("schoolId").in(schoolIds);

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
        AggregationResults<GuangDongDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalCallInfo", GuangDongDataStatistics.class);
        List<GuangDongDataStatistics> list = outputType.getMappedResults();
        Set<Integer> existSchoolId = new HashSet<>();
        for (GuangDongDataStatistics data : list) {
            data.setTotalCallNum(data.getCallNum());
            data.setTotalCallDuration(data.getCallDuration());
            records.add(data);
            existSchoolId.add(data.getSchoolId());
        }
        Criteria criteria2 = new Criteria();
        criteria2.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria2.and("schoolId").in(existSchoolId);
        Query query = new Query();
        query.addCriteria(criteria2);
        List<Integer> userIds = gpsMongoTemplate.findDistinct(query, "userId", "terminalCallInfo", Integer.class);
        List<SchoolUserDto> schoolUserList = prodUcMapper.getSchoolUserNumByUserId(userIds);

        for (GuangDongDataStatistics data : records) {
            for (SchoolUserDto schoolUserDto : schoolUserList) {
                if (data.getSchoolId().equals(schoolUserDto.getSchoolId())) {
                    data.setCallUserNum(schoolUserDto.getUserNum());
                    break;
                }
            }
        }
        return records;
    }

    //获取学校用户主动定位次数，主动定位用户数
    private List<GuangDongDataStatistics> getSchoolRequestLocationNum(Date start, Date end, List<Integer> schoolIds) {
        log.info("获取学校用户主动定位次数，主动定位用户数开始");
        List<GuangDongDataStatistics> records = new ArrayList<>();
        String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
        String endTime = String.format("%08x", end.getTime() / 1000) + "0000000000000000";
        Criteria criteria = new Criteria();
        criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria.and("schoolId").in(schoolIds);
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
        AggregationResults<GuangDongDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "userPositioning", GuangDongDataStatistics.class);
        List<GuangDongDataStatistics> list = outputType.getMappedResults();
        List<Integer> existSchoolId = new ArrayList<>();
        for (GuangDongDataStatistics data : list) {
            existSchoolId.add(data.getSchoolId());
            data.setTotalRequestLocationNum(data.getRequestLocationNum());
            records.add(data);
        }
        Criteria criteria2 = new Criteria();
        criteria2.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria2.and("schoolId").in(existSchoolId);
        Query query = new Query();
        query.addCriteria(criteria2);
        List<Integer> userIds = gpsMongoTemplate.findDistinct(query, "userId", "userPositioning", Integer.class);
        List<SchoolUserDto> schoolUserList = prodUcMapper.getSchoolUserNumByUserId(userIds);
        for (GuangDongDataStatistics data : records) {
            for (SchoolUserDto schoolUserDto : schoolUserList) {
                if (data.getSchoolId().equals(schoolUserDto.getSchoolId())) {
                    data.setRequestLocationUserNum(schoolUserDto.getUserNum());
                    break;
                }
            }
        }
        return records;
    }

    //获取学校sos、围栏越界告警
    private List<GuangDongDataStatistics> getSchoolAlarmNum(Date start, Date end, List<Integer> schoolIds) {
        log.info("获取学校sos、围栏越界告警开始");
        List<GuangDongDataStatistics> records = new ArrayList<>();
        String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
        String endTime = String.format("%08x", end.getTime() / 1000) + "0000000000000000";

        //sos告警统计
        Criteria criteria = new Criteria();
        criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria.and("alarmType").is(1);
        criteria.and("schoolId").in(schoolIds);
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

        AggregationResults<GuangDongDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalLocationBound", GuangDongDataStatistics.class);
        List<GuangDongDataStatistics> list = outputType.getMappedResults();
        List<Integer> existSchoolId = new ArrayList<>();
        for (GuangDongDataStatistics data : list) {
            existSchoolId.add(data.getSchoolId());
            data.setTotalSosAlarmNum(data.getSosAlarmNum());
            records.add(data);
        }
        Criteria criteria3 = new Criteria();
        criteria3.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria3.and("schoolId").in(existSchoolId);
        criteria3.and("alarmType").is(1);
        Query query = new Query();
        query.addCriteria(criteria3);
        List<Integer> userIds = gpsMongoTemplate.findDistinct(query, "userId", "terminalLocationBound", Integer.class);
        List<SchoolUserDto> schoolUserList = prodUcMapper.getSchoolUserNumByUserId(userIds);
        for (GuangDongDataStatistics data : records) {
            for (SchoolUserDto schoolUserDto : schoolUserList) {
                if (data.getSchoolId().equals(schoolUserDto.getSchoolId())) {
                    data.setSosAlarmUserNum(schoolUserDto.getUserNum());
                    break;
                }
            }
        }


        //围栏越界告警统计
        Criteria criteria2 = new Criteria();
        criteria2.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
        criteria2.and("alarmType").is(2);
        criteria2.and("schoolId").in(schoolIds);
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
        AggregationResults<GuangDongDataStatistics> outputType2 = gpsMongoTemplate.aggregate(aggregation2, "terminalLocationBound", GuangDongDataStatistics.class);
        List<GuangDongDataStatistics> list2 = outputType2.getMappedResults();
        for (GuangDongDataStatistics data : list2) {
            int index = records.indexOf(data);
            if (index >= 0) {
                GuangDongDataStatistics record = records.get(index);
                record.setTotalRegionalAlarmNum(data.getRegionalAlarmNum());
                record.setRegionalAlarmNum(data.getRegionalAlarmNum());
            } else {
                records.add(data);
            }
        }
        return records;
    }

    //获取学校用户安全定位次数,查询七天，数据在乘4
    private List<GuangDongDataStatistics> getSchoolLocationNum(Date start, Date end, List<Integer> schoolIds) {
        log.info("获取学校用户安全定位次数开始");
        int day = DateUtils.daysBetweenDate(start, end);
        List<GuangDongDataStatistics> records = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < day; i++) {
            if (i < 10) {
                continue;
            }
            j++;
            if (j > 7) {
                break;
            }
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            String startTime = String.format("%08x", start.getTime() / 1000) + "0000000000000000";
            String endTime = String.format("%08x", newEnd.getTime() / 1000) + "0000000000000000";

            Criteria criteria = new Criteria();
            criteria.and("_id").gte(new BsonObjectId(new ObjectId(startTime))).lt(new BsonObjectId(new ObjectId(endTime)));
            criteria.and("schoolId").in(schoolIds);
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
            AggregationResults<GuangDongDataStatistics> outputType = gpsMongoTemplate.aggregate(aggregation, "terminalLocationInfo", GuangDongDataStatistics.class);
            List<GuangDongDataStatistics> list = outputType.getMappedResults();
            for (GuangDongDataStatistics data : list) {
                int index = records.indexOf(data);
                if (index >= 0) {
                    GuangDongDataStatistics record = records.get(index);
                    record.setReportLocationNum(record.getReportLocationNum() + data.getReportLocationNum());
                } else {
                    records.add(data);
                }
            }
        }

        for (GuangDongDataStatistics data : records) {
            data.setReportLocationNum(data.getReportLocationNum() * 4);
        }

        return records;
    }

    //获取学校解绑数
    private List<GuangDongDataStatistics> getSchoolUnBindNum(Date start, Date end, List<Integer> schoolIds) {
        List<GuangDongDataStatistics> records = new ArrayList<>();
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
            List<SchoolWeekDataStatistics> unbindNums = prodUcMapper.getSchoolUnBindNumByStudetnId(studentId);
            for (SchoolWeekDataStatistics data : unbindNums) {
                if (schoolIds.contains(data.getSchoolId())) {
                    GuangDongDataStatistics data1 = new GuangDongDataStatistics();
                    BeanUtils.copyProperties(data, data1);
                    records.add(data1);
                }
            }
        }
        return records;
    }


    private void getCallInfoHistory(GuangDongDataStatistics data) {
        SchoolWeekDataStatistics weekData = schoolWeekDataStatisticsMapper.getBySchoolId(data.getSchoolId());
        if (weekData != null) {
            data.setTotalCallNum(weekData.getTotalCallNum());
            data.setTotalCallDuration(weekData.getTotalCallDuration());
        }
    }
}
