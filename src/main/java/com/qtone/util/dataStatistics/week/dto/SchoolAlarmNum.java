package com.qtone.util.dataStatistics.week.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 16:42 2023/6/5
 * @Modified By:
 */
@Data
public class SchoolAlarmNum {
    private Integer schoolId;
    //1,sos;2围栏越界
    private Integer alarmType;
    private Integer num;
}
