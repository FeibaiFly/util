package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 11:37 2024/1/23
 * @Modified By:
 */
@Data
public class UserLocationDetailInfo {
    private String province;
    private String city;
    private String district;
    private Integer userId;
    private String studentName;
    private Integer locationNum=0;
    private String imei;
    private String imeiPhone;
    private String parentTel;

    @Override
    public String toString() {
        return province+","+city+","+district+","+studentName+","+locationNum+","+imei+","+imeiPhone+","+parentTel;
    }
}
