package com.qtone.util.saveSeniorCardData.dto;

import cn.hutool.core.date.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 位置上报表
 * </p>
 *
 * @author guixiong
 * @since 2020-07-03
 */
@Data
public class TerminalLocationInfo implements Serializable {
    /**
     * terminal_location_info
     */
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
     * 经度 小数形式
     */
    private String lon = "";

    /**
     * 纬度，小数形式
     */
    private String lat = "";

    /**
     * 上报时间
     */
    private String locationTime = "";

    /**
     * 地址
     */
    private String locationAddr = "";

    /**
     * 报文信息id
     */
    private String reportId = "";

    /**
     * 创建时间
     */
    private String createTime = "";

    private Date timeout = DateUtil.offsetDay(new Date(), 15);

    //定位精度半径，单位：米-
    private String radius = "";
    //国家
    private String country = "";
    //省
    private String province = "";
    //市
    private String city = "";
    //城市编码
    private String citycode = "";
    //区域编码
    private String adcode = "";
    //道路名
    private String road = "";
    //街道名
    private String street = "";
    //定位附近的 poi 名称
    private String poi = "";
    private String mode = "";
    private String body = "";
    //状态，0没有解析或限流，1解析成功，2解析失败（没有位置信息）
    private Integer status;


}
