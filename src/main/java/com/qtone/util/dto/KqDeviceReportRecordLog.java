package com.qtone.util.dto;

import lombok.Data;

/**
 * 考勤记录上报日志
 *
 * @Author yangfei
 * @Date 2020/12/08 15:55
 * @Description
 */
@Data
public class KqDeviceReportRecordLog {
    // 设备名称
    private String deviceName;
    // 设备code
    private String deviceCode;
    // 设备厂家名称
    private String deviceTypeStr;
    // 设备厂家名称
    private String factoryTypeStr;
    // 上报时间
    private Long reportTime;

    private String optStr;
    // 用户标识
    private String userTag;
    // 用户id
    private Integer userId;
    // 用户名称
    private String userName;
}
