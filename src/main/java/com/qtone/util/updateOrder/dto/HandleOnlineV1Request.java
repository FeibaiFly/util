package com.qtone.util.updateOrder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: zjd
 * @date: 2019/11/21 16:29
 */
@Data
public class HandleOnlineV1Request {

    private Integer id;

    @JsonProperty(value = "GTID")
    //流水号
    private String GTID;

    //卡号必填
    private String cardNum;

    //"用户id必填"
    private Integer userId;

    //"终端编号必填"
    private String machineCode;

    //type数据类型：1：消费 2：退款
    private Integer type;

    //原消费的GTID，只有退款时才有值
    private String costGTID;

    //"金额必填"
    private BigDecimal fee;

    //"消费类型必填"
    //消费类型：1随机、2定价、3定值
    private Integer costWay;

    //1 账户扣款  2 签约扣款 3.人脸离线扣费
    private Integer costType;

    //"消费时间必填"
    private String costTime;
    //餐次id
    private Integer mealId;

    private Integer isOnline;

    private Integer status;

    private Integer schoolId;
    private String wxUserId;

    //支付凭证 由刷脸SDK返回，作为扣款唯一凭证
    private String pay_credential;
    //商户信息
    private SaveFaceTransactionV2Request.MerchantInfo merchant_info;
    //金额信息
    private SaveFaceTransactionV2Request.TradeAmountInfo trade_amount_info;
    //支付场景信息
    private SaveFaceTransactionV2Request.SceneInfo scene_info;
    //设备信息
    private SaveFaceTransactionV2Request.DeviceInfo device_info;
    //商品信息
    private String description;
    //商户附加信息
    private String attach;
    //商户订单号
    private String out_trade_no;
    //业务信息
    private SaveFaceTransactionV2Request.BusinessInfo business_info;

}
