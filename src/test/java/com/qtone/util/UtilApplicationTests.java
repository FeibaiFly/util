package com.qtone.util;

import com.qtone.util.dataStatistics.CardOrderRequest;
import com.qtone.util.dataStatistics.month.MonthDataStatisticsService;
import com.qtone.util.dataStatistics.week.WeeklyDataStatisticsService;
import org.hibernate.validator.constraints.pl.REGON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilApplicationTests {

    @Resource
    WeeklyDataStatisticsService weeklyDataStatisticsService;
    @Resource
    MonthDataStatisticsService monthDataStatisticsService;
    @Resource
    TestToProdCard testToProdCard;

    /**
     * 周报数据统计（ID1002125）
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/12 10:16
     */

    @Test
    public void statisticsWeekData() throws Exception {
        CardOrderRequest request = new CardOrderRequest();
        String startTime = "2023-06-05 00:00:00";
        String endTime = "2023-06-12 00:00:00";
        String fileName = "D:\\Deskop\\周报\\全国周报数据.xls";
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setFilePath(fileName);
        weeklyDataStatisticsService.statisticsWeekData(request);
    }

    /**
     * 陕西周报数据
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/12 10:16
     */

    @Test
    public void statisticsSxWeekData() throws Exception {
        CardOrderRequest request = new CardOrderRequest();
        String startTime = "2023-06-30 00:00:00";
        String endTime = "2023-07-07 00:00:00";
        String fileName = "D:\\Deskop\\周报\\陕西周报数据.xls";
        request.setFilePath(fileName);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        weeklyDataStatisticsService.statisticsShanxiWeekData(request);
    }

    @Test
    public void statisticsSxWeekData2() throws Exception {
        CardOrderRequest request = new CardOrderRequest();
        String startTime = "2023-06-01 00:00:00";
        String endTime = "2023-07-01 00:00:00";
        String fileName = "D:\\Deskop\\周报\\陕西月报数据.xlsx";
        request.setFilePath(fileName);
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        weeklyDataStatisticsService.statisticsShanxiWeekData2(request);
    }
    /**
     * 广东月报数据统计（ID1002112）
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/12 10:16
     */

    @Test
    public void statisticsMonthData() throws Exception {
        CardOrderRequest request = new CardOrderRequest();
        String startTime = "2023-06-01 00:00:00";
        String endTime = "2023-07-01 00:00:00";
        String fileName = "D:\\Deskop\\周报\\月报数据.xls";
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        request.setFilePath(fileName);
        monthDataStatisticsService.statisticsGuangdong(request);
    }


    /**
     * 江西上饶数据统计（是否活跃通过是否通话来判断）
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/12 10:16
     */

    @Test
    public void statisticsShangRaoMonthData() throws Exception {
        CardOrderRequest request = new CardOrderRequest();
        String fileName = "D:\\Deskop\\周报\\上饶月报数据.xls";
        request.setFilePath(fileName);
        monthDataStatisticsService.statisticsData2(request);
    }


    /**
     * 统计全国每月数据源
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/6/12 10:16
     */

    @Test
    public void statisticsCountryMonthData() throws Exception {
        CardOrderRequest request = new CardOrderRequest();
        String startTime = "2023-10-01 00:00:00";
        String endTime ="2023-11-01 00:00:00";
        request.setStartTime(startTime);
        request.setEndTime(endTime);
        weeklyDataStatisticsService.statisticsCountryMothData(request);
    }

    @Test
    public void testToProdCard() throws Exception {
        testToProdCard.testToProdCard(400043557);
    }

    /**
     * 统计考勤信息
     * @author:zhangpk
     * @param:
     * @return:
     * @date: 2023/7/21 14:37
     */
    @Test
    public void statisticsAttendanceInfo() throws Exception {
        String startTime = "2023-06-05 00:00:00";
        String endTime = "2023-06-10 00:00:00";
        Date start = DateUtils.getDate(startTime);
        Date end = DateUtils.getDate(endTime);
        int day = DateUtils.daysBetweenDate(start, end);
        CardOrderRequest request = new CardOrderRequest();
        request.setProvinceId(3191);

        for(int i=0;i<day;i++){
            start = DateUtils.addDate(end, -(day - i));
            Date newEnd = DateUtils.addDate(start, 1);
            request.setStartTime(DateUtils.formatDateTime(start));
            request.setEndTime(DateUtils.formatDateTime(newEnd));
            String fileName = "D:\\Deskop\\周报\\考勤"+DateUtils.formatDate(start)+"数据.xlsx";
            request.setFilePath(fileName);
            weeklyDataStatisticsService.statisticsAttendanceInfo(request);
        }

    }
}
