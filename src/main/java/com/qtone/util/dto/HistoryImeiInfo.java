package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 17:20 2024/1/12
 * @Modified By:
 */
@Data
public class HistoryImeiInfo {

    private String provinceName;
    private String cityName;
    private String districtName;
    private String schoolName;
    private Integer userId;
    private String userName;
    private Integer isStudentExist=0;
    private String imei;
    private String rfid;
    private String imeiPhone;
    private Integer bindAction=1;
    private String createTime;
    private String parentTel;

    @Override
    public String toString() {
        return
                provinceName + ',' +cityName + ',' +districtName + ',' +schoolName + ',' +userId + ',' +userName + ',' +isStudentExist + ',' +imei + ',' + rfid + ',' +imeiPhone + ',' +bindAction + ',' +createTime + ',' +parentTel;
    }
}
