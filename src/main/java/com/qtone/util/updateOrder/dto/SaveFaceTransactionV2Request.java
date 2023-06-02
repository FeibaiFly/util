package com.qtone.util.updateOrder.dto;

import lombok.Data;

/**
 * @author: zjd
 * @date: 2020/6/7 9:40 下午
 */
@Data
public class SaveFaceTransactionV2Request {



    @Data
    public static class MerchantInfo{
        //商户号
        private String mchid;
        //子商户号
        private String sub_mchid;
        //商户公众号
        private String appid;
        //子商户公众号z
        private String sub_appid;

    }

    @Data
    public static class TradeAmountInfo{
        //金额 订单总金额，单位分，只能为整数
        private Integer amount;
        //货币类型 符合ISO 4217标准的三位字母代码，目前仅支持人民币：CNY
        private String currency ="CNY";
    }

    @Data
    public static class SceneInfo{
        //设备IP
        private String device_ip;
    }

    @Data
    public static class DeviceInfo{
        //设备mc地址
        private String mac;
    }

    @Data
    public static class BusinessInfo{
        //平台产品ID K12项目传固定值传2
        private Integer business_product_id=2;
        //平台场景ID 3 食堂 4 超时 5 校医院 6 测试场景
        // 目前仅为3的情况支持垫资
        private Integer business_scene_id;
    }
}
