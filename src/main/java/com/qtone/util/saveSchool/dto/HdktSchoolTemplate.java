package com.qtone.util.saveSchool.dto;

import lombok.Data;

/**
 * @Description:
 * @Author: huangguangxi
 * @Date: 2021/7/19 17:33
 * * @param null
 *return
 **/
@Data
public class HdktSchoolTemplate {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String schoolName;

    /**
     * 省级ID
     */
    private Integer provinceId;

    /**
     * 省及名称
     */
    private String provinceName;

    /**
     * 地市名称
     */
    private String cityName;
    /**
     * 地市ID
     */
    private Integer cityId;

    /**
     * 区县ID
     */
    private Integer districtId;
    /**
     * 区县名称
     */
    private String districtName;

    /**
     * 学段id
     */
    private Integer section;

    /**
     * 1互动课堂平台，2教育云平台，3互动课堂
     */
    private String platformIds;

    private String platformAppIds;

    /**
     * 合约类型，1试用，2签约
     */
    private Integer contractType;

    /**
     * 合约结束时间
     */
    private String expireDate;

    /**
     * 试用日期（月份）
     */
    private Integer trialMonth;

    /**
     * 所用的校园卡类型（目前字典为 2.0， 2.5， 3.0）；
     */
    private Integer cardType;

    /**
     * 所有的校园卡厂家（目前字典为天波，思特，卡尔）；
     */
    private Integer cardFactory;

    /**
     * 第三方来源
     */
    private Integer thirdSource;

    /**
     * 消息推送方式，1公众号，2短信
     */
    private Integer messageType;

    /**
     * 学校消息推送设置公众账号ID
     */
    private String appid;

    private String appSecret;

    private Integer status;

    private String templateId;

    private Integer downloadNum;

    private String provinceCode;
}
