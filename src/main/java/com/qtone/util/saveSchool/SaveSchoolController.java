package com.qtone.util.saveSchool;

import com.alibaba.fastjson.JSONObject;
import com.qtone.util.MpAesUtilProd;
import com.qtone.util.PoiUtil;
import com.qtone.util.StringUtils;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dao.test.TestUcMapper;
import com.qtone.util.dataStatistics.week.dto.BindInfo;
import com.qtone.util.saveSchool.dto.HdktSchool;
import com.qtone.util.saveSchool.dto.HdktSchoolTemplate;
import com.qtone.util.saveSchool.dto.SchoolArea;
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
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 13:55 2023/5/17
 * @Modified By:
 */
@Controller
@Slf4j
public class SaveSchoolController {

    @Autowired
    TestUcMapper testUcMapper;
    @Autowired
    ProdUcMapper prodUcMapper;
    @Autowired
    HttpUtils httpUtils;

    @RequestMapping(value = "/save/school", method = RequestMethod.POST)
    @ResponseBody
    public void saveSchool() throws IOException {

        String fileUrl = "D:\\Deskop\\批量新增学校\\莆田批量新建校清单.xlsx";
        File file = new File(fileUrl);
        List<String[]> values = PoiUtil.readExcel(file);


        String url = "https://cardapi.hejiaoyu.com.cn/uc/api/school/template/sync/school/v1";
        String token = "877583FBD02421CB409958DBD67481071A5F0E15761B02ED3592FBF4D3E67A20F8563452A8B6EE9EACD9FCE2247B7365";
        Integer provinceId = 1252;
        Integer cityId = 1276;
        HdktSchoolTemplate schoolTemplate = new HdktSchoolTemplate();
        schoolTemplate.setProvinceId(1252);
        schoolTemplate.setProvinceCode("350000");
        schoolTemplate.setProvinceName("福建省");
        schoolTemplate.setCityId(1276);
        schoolTemplate.setCityName("莆田市");
        schoolTemplate.setExpireDate("2099-06-26 00:00:00");
        schoolTemplate.setContractType(2);
        List<SchoolArea> district = prodUcMapper.getSchoolAreaByParentId(cityId);
        Map<String,SchoolArea> districtMap = district.stream().collect(Collectors.toMap(SchoolArea::getDistrict, Function.identity(), (key1, key2) -> key2));
        List<String> failSchool = new ArrayList<>();
        for (int i=1;i<values.size();i++) {

                String[] arr = values.get(i);
                String districtName = arr[1];
                String schoolName = arr[2];
            try {
                schoolName = filterSpecialChar(schoolName);
                HdktSchool school= prodUcMapper.getSchoolByName(schoolName,provinceId);
                if(school!=null){
                    continue;
                }
                if(districtName.equals("市直辖")){
                    districtName="市辖区";
                }
                if(districtName.equals("莆田市湄洲湾北岸经济开发区")){
                    districtName="湄洲湾北岸经济开发区";
                }
                SchoolArea schoolArea = districtMap.get(districtName);
                if(schoolArea!=null){
                    schoolTemplate.setDistrictId(schoolArea.getId());
                    schoolTemplate.setDistrictName(districtName);
                }
                schoolTemplate.setSchoolName(schoolName);
                Integer section = getSectionBySchoolName(schoolName);
                schoolTemplate.setSection(section);
                Map<String,Object> paramMap = new HashMap<>();
                paramMap.put("thirdSource",0);
                paramMap.put("school",schoolTemplate);
                Map<String, Object> returnMap = httpUtils.postHttpMethod(url, JSONObject.toJSONString(paramMap), token);
                if (StringUtils.isNotNullToObj(returnMap)) {
                    Integer code = StringUtils.objToInt(returnMap.get("code"));
                    if(code!=200) {
                        String fail = "莆田市,"+arr[1]+","+arr[2]+","+StringUtils.objToInt(returnMap.get("msg"));
                        failSchool.add(fail);
                    }
                }else {
                    String fail = "莆田市,"+arr[1]+","+arr[2];
                    failSchool.add(fail);
                }
            } catch (Exception e) {
                String fail = "莆田市,"+arr[1]+","+arr[2];
                failSchool.add(fail);
                e.printStackTrace();
            }
            System.out.println(failSchool.toString());
        }
    }

    public static int getSectionBySchoolName(String schoolName){

        if(schoolName.contains("幼儿园")) {
            return 1;
        } else if(schoolName.contains("小学")) {
            return 2;
        } else if(schoolName.contains("高中") || schoolName.contains("高级中学")) {
            return 5;
        } else if(schoolName.contains("中学") || schoolName.contains("初中")) {
            return 4;
        } else {
            //默认：九年一贯制
            return 3;
        }
    }
    public static String filterSpecialChar(String sw) {
        if(StringUtils.isNullToObj(sw)) {
            return "无";
        }
        //特殊字符串
        String islv = "[\\s\\p{P}\\n\\r=+$￥<>^`~|]";
        sw = sw.replaceAll(islv, "").trim();
        if(StringUtils.isNullToObj(sw)){
            return "无";
        }
        return sw;
    }



    public static void main(String[] args) throws Exception {
        String fileName = "D:\\Deskop\\江苏绑卡学生信息.xlsx";
        String outfileName = "D:\\Deskop\\江苏绑卡学生信息2.xlsx";
        File file = new File(fileName);
        List<String[]> files = PoiUtil.readExcel(file,11);
        List<BindInfo> bindInfos = new ArrayList<>();
        for(int i=0;i<files.size();i++){
            BindInfo bindInfo = new BindInfo();
            bindInfo.setProvinceName(files.get(i)[0]);
            bindInfo.setCityName(files.get(i)[1]);
            bindInfo.setDistrictName(files.get(i)[2]);
            bindInfo.setSchoolName(files.get(i)[3]);
            bindInfo.setParentTel(MpAesUtilProd.decode(files.get(i)[4]));
            bindInfo.setImeiPhone(MpAesUtilProd.decode(files.get(i)[5]));
            bindInfo.setImei(files.get(i)[6]);
            bindInfo.setRfid(files.get(i)[7]);
            bindInfo.setCreateTime(files.get(i)[8]);
            bindInfo.setSendTime(files.get(i)[9]);
            bindInfo.setIsActive(files.get(i)[10]);
            bindInfos.add(bindInfo);
        }
        String[] header = {"省", "市", "区", "学校名称", "家长手机","插卡手号码","imei","rfid",  "初次绑卡时间", "绑卡时间", "是否活跃"};
        PoiUtil.createExcelXlsx(outfileName, "xxxx", header, bindInfos);

    }
}
