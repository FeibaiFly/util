package com.qtone.util.dataStatistics;

import lombok.Data;

/**
 * 电子学生卡订购接口请求参数
 */
@Data
public class CardOrderRequest {

    private Integer provinceId;

    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 导出的文件路径
     */
    private String filePath;

}
