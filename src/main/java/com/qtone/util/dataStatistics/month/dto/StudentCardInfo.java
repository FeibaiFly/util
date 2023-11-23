package com.qtone.util.dataStatistics.month.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 15:35 2023/6/8
 * @Modified By:
 */
@Data
public class StudentCardInfo {
    /**
     * 省
     */
    private String provinceName;

    /**
     * 市
     */
    private String cityName;

    /**
     * 区
     */
    private String districtName;


    /**
     * 学校名称
     */
    private String schoolName;

    private Integer studentId;

    private String studentName;

    private String parentName;

    private String parentTel;
    private String imeiPhone;
    private String sendTime;
    private String createTime;
    private String imei;
    private String is5Active="否";
    private String is4Active="否";
    private String is3Active="否";
    private String is2Active="否";
    private String is1Active="否";
}
