package com.qtone.util.xml;

import lombok.Data;

import java.util.List;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 17:16 2023/7/13
 * @Modified By:
 */
@Data
public class operation_out {
    private String resp_code;
    private String resp_desc;
    private List<order_record> order_records;

    @Data
    public static  class order_record{
        private String offer_id;
        private String eff_date;
        private String exp_date;
        private String msisdn;
        private String oper_type;
        private String oper_time;
    }
}
