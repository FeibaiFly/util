package com.qtone.util.updateOrder.entity;

/**
  * @Author: zhangpk
  * @Description:
  * @Date:Created in 14:24 2023/5/17
  * @Modified By:
*/

import lombok.Data;

/**
    * 订单请求失败，本地重试记录表
    */
@Data
public class OrderRequest {
    private Integer id;

    private String gtid;

    private String paramstring;

}