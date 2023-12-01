package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 13:47 2023/12/1
 * @Modified By:
 */
@Data
public class ImeiAreaInfo {
    private String imei="";
    private String provinceName="";
    private String cityName="";
    private String districtName="";
    private String factoryName="";
    private String deviceTypeName="";

    @Override
    public String toString() {
        return  imei+","+provinceName+","+cityName+","+districtName+","+factoryName+","+deviceTypeName;
    }
}
