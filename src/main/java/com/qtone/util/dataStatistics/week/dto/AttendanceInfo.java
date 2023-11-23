package com.qtone.util.dataStatistics.week.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 14:14 2023/7/21
 * @Modified By:
 */
@Data
public class AttendanceInfo {
    private String provinceName;
    private String cityName;
    private String districtName;
    private Integer schoolId;
    private String schoolName;
    //绑卡数
    private Integer bindNum;
    private String deviceName;
    private String date;
    //考勤人数
    private Integer attendanceNum=0;
}
