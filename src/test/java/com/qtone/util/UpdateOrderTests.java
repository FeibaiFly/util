package com.qtone.util;

import com.alibaba.fastjson.JSONObject;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dao.test.TestUcMapper;
import com.qtone.util.updateOrder.dto.HandleOnlineV1Request;
import com.qtone.util.zxx.util.HttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UpdateOrderTests {

    @Autowired
    TestUcMapper testUcMapper;
    @Autowired
    HttpUtils httpUtils;

    @Test
    public void updateOrder() {
        List<String> fileUrls = new ArrayList<>();
//        fileUrls.add("D:\\Deskop\\智学互动\\阿里云待处理订单\\result\\10030823-result.txt");
        fileUrls.add("D:\\Deskop\\智学互动\\阿里云待处理订单\\10030933_11_02-result.txt");
//        fileUrls.add("D:\\Deskop\\智学互动\\阿里云待处理订单\\result\\10030934-result.txt");
//        fileUrls.add("D:\\Deskop\\智学互动\\阿里云待处理订单\\result\\10030952-result.txt");
//        fileUrls.add("D:\\Deskop\\智学互动\\阿里云待处理订单\\result\\10030963-result.txt");
//        fileUrls.add("D:\\Deskop\\智学互动\\阿里云待处理订单\\result\\10030988-result.txt");

        Map<String, HandleOnlineV1Request> map = new HashMap<>();
        for(String fileUrl:fileUrls) {
            List<String> fileString = fileToData(fileUrl);
            for (String orderStr : fileString) {
//                String[] arr = orderStr.split("请求参数:");

                String[] arr = orderStr.split("数据处理：==============");
                try {
//                    List<HandleOnlineV1Request> orders = JSONObject.parseArray(arr[1], HandleOnlineV1Request.class);
//                    for(HandleOnlineV1Request order:orders) {
//                        if (!map.containsKey(order.getGTID())){
//                            map.put(order.getGTID(),order);
//                        }
//                    }
                    HandleOnlineV1Request orderInfo = JSONObject.parseObject(arr[1],HandleOnlineV1Request.class);
                    map.put(orderInfo.getGTID(),orderInfo);
                } catch (Exception e) {
                    e.printStackTrace();
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
//                if(!order.getGTID().equals("GZAT421F1600688SGZN08880036567220231009063323")){
//                    continue;
//                }
                Map<String, Object> returnMap = httpUtils.postHttpMethod(url, paramString, null);
                System.out.println("gtid:"+order.getGTID()+","+ returnMap);
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
