package com.qtone.util.weeklyData;

import lombok.Data;

/**
 * 电子学生卡订购接口请求参数
 */
@Data
public class CardOrderRequest {

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    private String appKey;
    //1按日统计，2按传入的开始时间结束时间统计
    private Integer type;

}
