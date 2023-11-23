package com.qtone.util.xml;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 17:11 2023/7/13
 * @Modified By:
 */
@Controller
@Slf4j
public class TestXmlRequest {
//
//    produces= {"application/xml;charset=utf-8"} 这句作用是以xml形式返回，如果不写就以json格式返回了。
    @RequestMapping(value="/testxml" ,method= RequestMethod.POST,produces= {"application/xml;charset=utf-8"})
    @ResponseBody
    public operation_out getparent(@RequestBody operation_in pat){

        operation_out response = new operation_out();
        response.setResp_code("1");
        response.setResp_desc("成功");
        List<operation_out.order_record> order_records = new ArrayList<>();
        operation_out.order_record orderRecord = new operation_out.order_record();
        orderRecord.setMsisdn("订单一");
        orderRecord.setEff_date("2023-07-01 07:31:49");
        orderRecord.setExp_date("2023-07-01 07:31:49");
        orderRecord.setOffer_id("1111111111");
        orderRecord.setOper_time("2023-07-01 07:31:49");
        orderRecord.setOper_type("01");
        operation_out.order_record orderRecord2 = new operation_out.order_record();
        orderRecord2.setMsisdn("订单二");
        orderRecord2.setEff_date("2023-07-01 07:31:49");
        orderRecord2.setExp_date("2023-07-01 07:31:49");
        orderRecord2.setOffer_id("1111111111");
        orderRecord2.setOper_time("2023-07-01 07:31:49");
        orderRecord2.setOper_type("01");
        order_records.add(orderRecord);
        order_records.add(orderRecord2);
        response.setOrder_records(order_records);
        System.out.println("方法进来了");
        return response ;
    }
}
