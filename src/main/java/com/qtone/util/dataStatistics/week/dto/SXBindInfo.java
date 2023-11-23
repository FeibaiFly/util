package com.qtone.util.dataStatistics.week.dto;

import lombok.Data;

/**
  * @Author: zhangpk
  * @Description:
  * @Date:Created in 10:50 2023/6/6
  * @Modified By:
*/
@Data
public class SXBindInfo {

    private String provinceName;

    private String cityName;

    private String districtName;

    private String schoolName;

    private String imeiPhone;

    private String parentTel;

    private String createTime;

    private String sendTime;

    private String imei;

    private String isActive="否";

}