package com.qtone.util.dto;

import lombok.Data;

/**
 * <p>
 * 通话记录表
 * </p>
 *
 * @author guixiong
 * @since 2020-07-03
 */
@Data
public class TerminalCallInfo {

    private static final long serialVersionUID = 1L;

    private String id;

    private Integer userId;

    private String userName;

    private Integer userType;

    private String userTel;

    private String cardNum;

    private String imei;
    //插卡号码
    private String imeiPhone;

    private Integer schoolId;

    private String schoolName;

    private Integer classId;

    private String className;

    private Integer gradeId;

    private String gradeName;

    /**
     * 目标号码
     */
    private String targetPhone;

    /**
     * 开始时间yyyyMMddHHmmss
     */
    private String startTime;
    /**
     * 结束时间yyyyMMddHHmmss
     */
    private String endTime;

    /**
     * 时长 单位：秒
     */
    private Integer duration;

    /**
     * 呼入/呼出  0=呼入，1=呼出
     */
    private Integer callType;

    private String createTime;

    /**
     * 附属字段
     */
    private String callTypeStr;

    private String durationStr;

}
