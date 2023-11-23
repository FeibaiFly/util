package com.qtone.util.saveSeniorCardData.dto;

import lombok.Data;

/**
 * <p>
 * 越界上报表
 * </p>
 *
 * @author guixiong
 * @since 2020-07-03
 */
@Data
public class TerminalLocationBound {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 用户电话号码
     */
    private String userTel;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 设备号imei,其他表中的teno字段
     */
    private String imei;

    /**
     * 学校id
     */
    private Integer schoolId;


    /**
     * 经度，小数点
     */
    private String lon;

    /**
     * 纬度，小数点
     */
    private String lat;

    /**
     * 告警设置id
     */
    private Integer alarmId;

    private Integer regionalType;

    /**
     * 区域告警设置名称
     */
    private String alarmName;

    /**
     * 告警类型，圆还是方
     */
    private String shape;

    /**
     * 告警元素值
     */
    private String elementValue;

    /**
     * 位置时间
     */
    private String locationTime;

    /**
     * 位置
     */
    private String locationAddr;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 删除时间
     */
    private String deleteTime;

    //告警类型：(1-sos 告警 2-低电量告警 3-进入围栏告警 4-离开围栏告警 5关机告警)
    private Integer alarmType;

    /**
     * 报警类型：1-实情(默认)   2-误报
     */
    private Integer sosType;

    /**
     * 报警状态：1-待处理(默认)   2-已关闭
     */
    private Integer sosStatus;
}
