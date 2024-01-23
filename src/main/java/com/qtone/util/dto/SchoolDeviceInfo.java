package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 11:05 2023/12/13
 * @Modified By:
 */
@Data
public class SchoolDeviceInfo {
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

    private Integer schoolId;
    /**
     * 学校名称
     */
    private String schoolName;

    private String imeiPhone;
    private String imei;
}
