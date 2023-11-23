package com.qtone.util.qinqingwang;

import com.alibaba.fastjson.JSONObject;
import com.qtone.util.DateUtils;
import com.qtone.util.PoiUtil;
import com.qtone.util.StringUtils;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dao.test.TestUcMapper;
import com.qtone.util.mpAes.MpAesUtilTest;
import com.qtone.util.qinqingwang.entity.FamilyPhoneMemberRecord;
import com.qtone.util.qinqingwang.entity.GSFamilyGroupMember;
import com.qtone.util.qinqingwang.entity.GsCardOrderInfo;
import com.qtone.util.qinqingwang.entity.PhoneMsg;
import com.qtone.util.zxx.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

@Controller
public class QinQingWang {
    @Autowired
    HttpUtils httpUtils;
    @Autowired
    ProdUcMapper prodUcMapper;
    @Autowired
    TestUcMapper testUcMapper;

    @RequestMapping(value = "/add/qinqingwang", method = RequestMethod.POST)
    @ResponseBody
    public void saveResource(@RequestBody Map<String, String> param, HttpServletRequest request, HttpServletResponse response) throws Exception, InterruptedException {

        String fileUrl = "C:\\Users\\17272\\Desktop\\添加亲情网成员脚本\\秦州学生证清单2.xlsx";
        File file = new File(fileUrl);
        List<String[]> values = PoiUtil.readExcel(file);
        List<String> phones = new ArrayList<>();
        List<String> encodePhones = new ArrayList<>();
        for (String[] value : values) {
            phones.add(value[1]);
            encodePhones.add(MpAesUtilTest.encode(value[1]));
        }
        String batchNumber = DateUtils.formatDateTime(new Date());
        List<FamilyPhoneMemberRecord> bindPhones = prodUcMapper.getBindImeiPhone(encodePhones);

        Map<String,FamilyPhoneMemberRecord> bindPhoneMap = new HashMap<>();
        for(FamilyPhoneMemberRecord phoneMemberRecord: bindPhones) {
            String imeiPhone = MpAesUtilTest.decode(phoneMemberRecord.getImeiPhone());
            String familyPhone = MpAesUtilTest.decode(phoneMemberRecord.getFamilyPhone());
            phoneMemberRecord.setImeiPhone(imeiPhone);
            phoneMemberRecord.setFamilyPhone(familyPhone);
            bindPhoneMap.put(imeiPhone,phoneMemberRecord);
        }

        phones.removeAll(bindPhoneMap.keySet());

        List<String> noGroupPhone = new ArrayList<>();
        List<String> haveGroupPhone = new ArrayList<>();
        List<PhoneMsg> allPhone = new ArrayList<>();

        for (String phone : phones) {
            allPhone.add(new PhoneMsg(phone, "订购号码未绑定用户"));
        }

        for (FamilyPhoneMemberRecord bindPhone : bindPhones) {
            String imeiPhone = bindPhone.getImeiPhone();
            if(StringUtils.isEmpty(bindPhone.getFamilyPhone())) {
                allPhone.add(new PhoneMsg(imeiPhone, "订购号码未在学生卡设置呼出号码"));
                continue;
            }

            //解析呼出号码，不包含SOS
            List<String> familyPhones = analysisFamilyPhone(bindPhone.getFamilyPhone());
            if(familyPhones.isEmpty()) {
                allPhone.add(new PhoneMsg(imeiPhone, "订购号码未在学生卡设置呼出号码"));
                continue;
            }
            bindPhone.setFamilyPhones(familyPhones);

            List<GsCardOrderInfo> orderInfos = queryGsCardOrderInfo(imeiPhone);
            if(orderInfos.isEmpty()) {
                allPhone.add(new PhoneMsg(imeiPhone, "订购号码未订购4g学生卡业务"));
                continue;
            }

            List<GSFamilyGroupMember> familyGroupMembers = queryGsFamilyGroupMembers(imeiPhone);
            if (familyGroupMembers == null || familyGroupMembers.size() == 0) {
//                allPhone.add(new PhoneMsg(decodePhone, "订购号码无群组"));
                noGroupPhone.add(imeiPhone);
                continue;
            }
            boolean haveGroupFlag = false;
            boolean haveMemberFlag = false;

            String labelId="";
            String labelName="";
            for (GSFamilyGroupMember gsFamilyGroupMember : familyGroupMembers) {
                if (imeiPhone.equals(gsFamilyGroupMember.getMAIN_ACCESS_NUM())) {
                    haveGroupFlag = true;
                    if (gsFamilyGroupMember.getMEMBEREXT_LIST().size() > 0) {
                        haveMemberFlag = true;
                    }
                    labelId = gsFamilyGroupMember.getPOID_CODE();
                    labelName = gsFamilyGroupMember.getPOID_LABLE();
                }
            }
            if (haveMemberFlag) {
                allPhone.add(new PhoneMsg(imeiPhone, "订购号码已有亲情网成员"));
                continue;
            }
            if (haveGroupFlag) {
//                allPhone.add(new PhoneMsg(decodePhone, "订购号码有群组无亲情网成员"));
                haveGroupPhone.add(imeiPhone);
                FamilyPhoneMemberRecord phoneMemberRecord = bindPhoneMap.get(imeiPhone);
                phoneMemberRecord.setLabelCode(labelId);
                phoneMemberRecord.setLabelName(labelName);
                continue;
            }
//            noGroupPhone.add(decodePhone);

//            allPhone.add(new PhoneMsg(decodePhone, "订购号码无群组"));
        }

        //将无群组的插卡号码添加群组
        for(String phone:noGroupPhone) {
            String returnStr = subfamilyGroupChangeInter(phone,1);
            if(!returnStr.equals("success")) {
                allPhone.add(new PhoneMsg(phone,"新建群组失败"));
            }
//            FamilyPhoneMemberRecord phoneMemberRecord = bindPhoneMap.get(phone);
//            phoneMemberRecord.setLabelCode("01");
//            phoneMemberRecord.setLabelName("01");
//            haveGroupPhone.add(phone);
        }
        Set<String> failPhones = new HashSet<>();
        for(String phone:haveGroupPhone) {
            try {
                FamilyPhoneMemberRecord phoneMemberRecord = bindPhoneMap.get(phone);
                List<String> familyPhones = phoneMemberRecord.getFamilyPhones();
                String familyPhoneStr = "";
                for(String familyPhone :familyPhones) {
                    String returnInfo = subfamilyGroupChangeInterMem(phone,familyPhone,1,phoneMemberRecord.getLabelCode(),phoneMemberRecord.getLabelName());
                    if (!returnInfo.equals("success")) {
                        phoneMemberRecord.setStatus(0);
                        phoneMemberRecord.setFailMsg(returnInfo);
                        familyPhoneStr +=familyPhone+"添加失败,";
                    } else {
                        phoneMemberRecord.setStatus(1);
                        phoneMemberRecord.setFailMsg("成功");
                        familyPhoneStr +=familyPhone+"添加成功,";
                    }
                    testUcMapper.insertFamilyPhoneMemberRecord(phone,phoneMemberRecord.getSchoolId(),phoneMemberRecord.getClassId(),
                            phoneMemberRecord.getUserId(),2,phoneMemberRecord.getProvinceId(),phoneMemberRecord.getCityId(),
                            phoneMemberRecord.getDistrictId(),batchNumber,phoneMemberRecord.getLabelCode(),phoneMemberRecord.getLabelName(),
                            familyPhone,1,phoneMemberRecord.getStatus(),returnInfo);
                }
                allPhone.add(new PhoneMsg(phone,"添加亲情网成员成功",familyPhoneStr));
            } catch (Exception e) {
                failPhones.add(phone);
                e.printStackTrace();
            }
        }
        String failPhoneStr = "";
        for(String failPhone:failPhones) {
            failPhoneStr +=failPhone+",";
        }
        System.out.println(failPhoneStr);
        String[] head = {"插卡号码", "msg","设置成功的亲情网成员"};


        PoiUtil.exportExcel("插卡号码亲情网匹配情况", "sheet1", head, allPhone, request, response);
    }

    /**
     * create by: zhangpk
     * description: 查询号码是否订购4g学生卡业务
     * create time: 11:00 2021/8/6
     *
     * @return
     * @Param: null
     */
    public List<GsCardOrderInfo> queryGsCardOrderInfo(String phone) throws Exception {
        String queryGsCardOrderInfoUrl = "http://117.156.17.155:9000/payment-server/card/getCardBillByDn";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("phones", phone);
//        logger.info("查询学生证活动订购关系接口:" + queryGsCardOrderInfoUrl + "  请求参数:" + JSON.toJSONString(paramMap));
        Map<String, Object> returnMap = httpUtils.postHttpMethod(queryGsCardOrderInfoUrl, paramMap, null);
//        logger.info(phone+"查询学生证活动订购关系接口返回：" + JSONObject.toJSONString(returnMap));
        List<GsCardOrderInfo> returnList = new ArrayList();
        if (returnMap != null && returnMap.size() > 0 && StringUtils.objToInt(returnMap.get("CODE")) == 0) {
            returnList = JSONObject.parseArray(StringUtils.objToStr(returnMap.get("DATA")), GsCardOrderInfo.class);
        }
        return returnList;
    }

    /**
     * create by: zhangpk
     * description: 查询亲情网成员信息
     * create time: 15:12 2021/8/6
     *
     * @return
     * @Param: null
     */
    public List<GSFamilyGroupMember> queryGsFamilyGroupMembers(String phone) throws Exception {
        String queryFamilyGroupMembersUrl = "http://117.156.17.155:9000/payment-server/card/queryfamilyGroupMembers";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ACCESS_NUM", phone);
        paramMap.put("PRODUCT_CODE", "MFC000001");
        Map<String, Object> returnMap = httpUtils.postHttpMethod(queryFamilyGroupMembersUrl, paramMap, null);
        List<GSFamilyGroupMember> returnList = new ArrayList<>();
        if (returnMap != null && returnMap.size() > 0 && StringUtils.objToInt(returnMap.get("CODE")) == 0) {
            Map<String, Object> map = JSONObject.parseObject(StringUtils.objToStr(returnMap.get("BUSI_INFO")), Map.class);
            if (map != null && map.get("RSP_RESULT") != null) {
                returnList = JSONObject.parseArray(StringUtils.objToStr(map.get("RSP_RESULT")), GSFamilyGroupMember.class);
            }
        }
        return returnList;
    }

    /**
     * create by: zhangpk
     * description: 全国亲情网组网，拆网预受理
     * create time: 15:12 2021/8/6
     *
     * @return
     * @Param: null
     */
    public String subfamilyGroupChangeInter(String phone, Integer groupType) throws Exception {
        String changeFamilyGroupUrl = "http://117.156.17.155:9000/payment-server/card/subfamilyGroupChangeInter";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ACCESS_NUM", phone);
        paramMap.put("GROUP_TYPE", groupType);
        paramMap.put("OFFER_ID", "102000007105");
        paramMap.put("POID_CODE", "01");
//        logger.info("请求全国亲情网组网，拆网预受理接口:" + changeFamilyGroupUrl + "  请求参数:" + JSON.toJSONString(paramMap));
        Map<String, Object> returnMap = httpUtils.postHttpMethod(changeFamilyGroupUrl, paramMap, null);
//        logger.info(phone+"全国亲情网组网，拆网预受理返回：" + JSONObject.toJSONString(returnMap));
        if (returnMap != null && returnMap.size() > 0 && StringUtils.objToStr(returnMap.get("MESSAGE")).equals("success")) {
            Map<String, String> map = JSONObject.parseObject(StringUtils.objToStr(returnMap.get("BUSI_INFO")), Map.class);
            if ("00".equals(map.get("RSP_CODE"))) {
                return "success";
            } else {
                return map.get("RSP_DESC");
            }
        }
        return StringUtils.objToStr(returnMap.get("MESSAGE"));
    }

    /**
     * create by: zhangpk
     * description: 全国亲情网成员添加，成员删除预受理
     * create time: 15:12 2021/8/6
     *
     * @return
     * @Param: null
     */
    public String subfamilyGroupChangeInterMem(String phone, String memberPhone, Integer groupType, String labelId, String memLabel) throws Exception {
        String changeFamilyGroupMemUrl = "http://117.156.17.155:9000/payment-server/card/subfamilyGroupChangeInterMem";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("MAIN_ACCESS_NUM", phone);
        paramMap.put("SUB_ACCESS_NUM", memberPhone);
        paramMap.put("GROUP_TYPE", groupType);
        paramMap.put("PRODUCT_CODE", "MFC000001");
        paramMap.put("POID_CODE", labelId);
        paramMap.put("MEM_LABLE", memLabel);
//        logger.info(phone+"全国亲情网成员添加，成员删除预受理接口:" + changeFamilyGroupMemUrl + "  请求参数:" + JSON.toJSONString(paramMap));
        Map<String, Object> returnMap = httpUtils.postHttpMethod(changeFamilyGroupMemUrl, paramMap, null);
//        logger.info(phone+"全国亲情网成员添加，成员删除预受理接口返回：" + JSONObject.toJSONString(returnMap));
        if (returnMap != null && returnMap.size() > 0 && StringUtils.objToStr(returnMap.get("MESSAGE")).equals("success")) {
            Map<String, String> map = JSONObject.parseObject(StringUtils.objToStr(returnMap.get("BUSI_INFO")), Map.class);
            if ("00".equals(map.get("RSP_CODE"))) {
                return "success";
            } else {
                return map.get("RSP_DESC");
            }
        }
        return StringUtils.objToStr(returnMap.get("MESSAGE"));
    }

    public List<String> analysisFamilyPhone(String targetTelStr) {

        targetTelStr = targetTelStr.replace("0=", "").replace("1=", "")
                .replace("2=", "").replace("3=", "").replaceAll(" ", "");
        String[] telStr = targetTelStr.split("!");
        List<String> tel = new ArrayList<>();
        try {
            for (int i = 0; i < telStr.length; i++) {
                if (StringUtils.isEmpty(telStr[i])) {
                    continue;
                }
                if (i == 0) {
                    continue;
                }
                tel.add(telStr[i]);
                continue;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tel;
    }
    @RequestMapping(value = "/matchExcel", method = RequestMethod.POST)
    @ResponseBody
    public void matchExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String batchNumber = "2022-10-10 17:37:00";
        String fileUrl = "C:\\Users\\17272\\Desktop\\添加亲情网成员脚本\\泰州亲情网数据生成情况.xls";
        File file = new File(fileUrl);
        List<String[]> values = PoiUtil.readExcel(file);
        Map<String,PhoneMsg> imeiPhoneMap = new HashMap<>();
        for(String[] value:values) {
            PhoneMsg phoneMsg = new PhoneMsg(value[0],value[1],value[2]);
            imeiPhoneMap.put(value[0],phoneMsg);
        }
        List<FamilyPhoneMemberRecord> phoneMemberRecords = testUcMapper.getFamilyPhoneMemberRecord(batchNumber);

        Map<String,String> failMsgMap = new HashMap<>();
        for(FamilyPhoneMemberRecord record:phoneMemberRecords) {
            String failMsg;
            if(record.getStatus()==1) {
                failMsg=record.getFamilyPhone()+"添加成功,";
            }else {
                failMsg=record.getFamilyPhone()+"添加失败,";
            }
            if(failMsgMap.containsKey(record.getImeiPhone())) {
                String msg = failMsgMap.get(record.getImeiPhone());
                failMsgMap.put(record.getImeiPhone(),msg+failMsg);
            }else {
                failMsgMap.put(record.getImeiPhone(),failMsg);
            }
        }

        for(String key:failMsgMap.keySet()) {
            if(imeiPhoneMap.containsKey(key)) {
                imeiPhoneMap.get(key).setFamilyPhone(failMsgMap.get(key));
            }
        }
        List<PhoneMsg> insertList = new ArrayList<>();
        for(String key:imeiPhoneMap.keySet()) {
            insertList.add(imeiPhoneMap.get(key));
        }



        String[] head = {"插卡号码", "msg","设置成功的亲情网成员"};


        PoiUtil.exportExcel("插卡号码亲情网匹配情况", "sheet1", head,insertList , request, response);

    }
}
