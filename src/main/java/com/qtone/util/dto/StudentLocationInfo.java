package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 17:26 2023/12/22
 * @Modified By:
 */
@Data
public class StudentLocationInfo {
    private Integer studentId;
    private String studentName;
    private String imei;
    private String lon;
    private String lat;
    private String locationTime;
}
