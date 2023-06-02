package com.qtone.util.updateOrder;

import com.alibaba.fastjson.JSONObject;
import com.qtone.util.StringUtils;
import com.qtone.util.dao.test.TestUcMapper;
import com.qtone.util.updateOrder.dto.HandleOnlineV1Request;
import com.qtone.util.zxx.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 13:55 2023/5/17
 * @Modified By:
 */
@Controller
@Slf4j
public class UpdateOrderController {

    @Autowired
    TestUcMapper testUcMapper;
    @Autowired
    HttpUtils httpUtils;

    @RequestMapping(value = "/update/order", method = RequestMethod.POST)
    @ResponseBody
    public void updateOrder() {
        List<String> fileUrls = new ArrayList<>();
        fileUrls.add("D:\\Deskop\\待处理订单\\10030933-result.txt");
        fileUrls.add("D:\\Deskop\\待处理订单\\10030988-result.txt");
//        fileUrls.add("D:\\Deskop\\待处理订单\\10030952-result.txt");

        Map<String,HandleOnlineV1Request> map = new HashMap<>();
        for(String fileUrl:fileUrls) {
            List<String> fileString = fileToData(fileUrl);
            for (String orderStr : fileString) {
                String[] arr = orderStr.split("请求参数:");
                List<HandleOnlineV1Request> orders = JSONObject.parseArray(arr[1], HandleOnlineV1Request.class);
                for(HandleOnlineV1Request order:orders) {
                    if (!map.containsKey(order.getGTID())){
                        map.put(order.getGTID(),order);
                    }
                }
            }
        }
        String url = "http://api.xuechengcloud.com/ecard/api/machine/offlineTransaction/v1";
        List<String> insertFailGtid = new ArrayList<>();
        List<String> postFailGtid = new ArrayList<>();
        for (HandleOnlineV1Request order : map.values()) {
            String gtid = testUcMapper.getOrderGtid(order.getGTID());
            if(StringUtils.isNotEmpty(gtid)){
                continue;
            }
            List<HandleOnlineV1Request> param = new ArrayList<>();
            param.add(order);
            String paramString = JSONObject.toJSONString(param);
            try {
                Map<String, Object> returnMap = httpUtils.postHttpMethod(url, paramString, null);
                log.info("gtid:"+order.getGTID()+","+ returnMap);
                if (StringUtils.isNotNullToObj(returnMap)) {
                    Integer code = StringUtils.objToInt(returnMap.get("code"));
                    if(code==200) {
                        try {
                            testUcMapper.insertOrderRequest(order.getGTID(),paramString);
                        } catch (Exception e) {
                            insertFailGtid.add(order.getGTID());
                        }
                    }else {
                        postFailGtid.add(gtid);
                    }
                }
            } catch (Exception e) {
                postFailGtid.add(gtid);
                e.printStackTrace();
            }
        }
        System.out.println("插入数据库失败："+insertFailGtid.toString());
        System.out.println("调用接口失败："+postFailGtid.toString());

    }

    public static void main(String[] args) {
        String fileUrl = "D:\\Deskop\\待处理订单\\10030963-result.txt";
        List<String> fileString = fileToData(fileUrl);
        List<HandleOnlineV1Request> orderList = new ArrayList<>();
        for (String orderStr : fileString) {
            String[] arr = orderStr.split("请求参数:");
            List<HandleOnlineV1Request> orders = JSONObject.parseArray(arr[1], HandleOnlineV1Request.class);
            orderList.addAll(orders);
        }
        System.out.println(orderList);
    }

    public static List<String> fileToData(String fileUrl) {
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileUrl)), "UTF-8"));//UTF-8
            String str = null;
            int i = 0;
            while ((str = br.readLine()) != null) {
                if (StringUtils.isNotBlank(str)) {
                    list.add(str);
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
