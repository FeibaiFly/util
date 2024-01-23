package com.qtone.util.province_gejie;

import com.qtone.util.DateUtils;
import com.qtone.util.TxtUtil;
import com.qtone.util.dao.prod.ProdGpsMapper;
import com.qtone.util.dao.prod.ProdUcMapper;
import com.qtone.util.dto.TerminalPhone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 9:58 2024/1/11
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TerminalPhoneGeJie {
    @Autowired
    ProdGpsMapper prodGpsMapper;

    /**
     * 增量割接
     */
    @Test
    public void addTerminalPhone() throws IOException {
        Integer provinceId= 875;
        String updateTime = "2023-12-05 12:00:00";
        String provinceAddDataFile = "D:\\Deskop\\智学互动\\省份割接\\江苏割接\\gps\\二级平台增量数据\\terminalPhone.txt";
        String outFile = "D:\\Deskop\\智学互动\\省份割接\\江苏割接\\gps\\terminal_phone.sql";
        List<String> list1 = TxtUtil.read(provinceAddDataFile);
        List<TerminalPhone> platformData = prodGpsMapper.getTerminalPhoneList(provinceId,updateTime);
        Map<Integer,String[]> provinceDataMap = new HashMap<>();
        List<String> outStringList = new ArrayList<>();
        for(String data:list1){
            String[] str = data.split("\t");
            String userId = str[1];
            provinceDataMap.put(Integer.valueOf(userId),str);
        }
        for(TerminalPhone terminalPhone:platformData){
            Integer userId = terminalPhone.getUserId();

            String[] str = provinceDataMap.get(Integer.valueOf(userId));
            String value ="";
            if(str==null){
//                value ="INSERT INTO `terminal_phone`(`user_id`, `user_name`, `user_type`, `user_tel`, `card_num`, `imei`, `iccid`, `phone`, `school_id`, `school_name`, `province_id`, `province_name`, `city_id`, `city_name`, `district_id`, `district_name`, `class_id`, `class_name`, `grade_id`, `grade_name`, `create_time`, `update_time`, `delete_time`, `is_deleted`) VALUES " + "("+terminalPhone.getUserId()+", '"+terminalPhone.getUserName()+"', "+terminalPhone.getUserType()+", "+terminalPhone.getUserTel()+", '"+terminalPhone.getCardNum()+"', '"+terminalPhone.getImei()+"', '"+terminalPhone.getIccid()+"', '"+terminalPhone.getPhone()+"', "+terminalPhone.getSchoolId()+", '"+terminalPhone.getSchoolName()+"', "+terminalPhone.getProvinceId()+", '"+terminalPhone.getProvinceName()+"', "+terminalPhone.getCityId()+", '"+terminalPhone.getCityName()+"', "+terminalPhone.getDistrictId()+", '"+terminalPhone.getDistrictName()+"', "+terminalPhone.getClassId()+", '"+terminalPhone.getClassName()+"', "+terminalPhone.getGradeId()+", '"+terminalPhone.getGradeName()+"', '"+ terminalPhone.getCreateTime() +"', '"+terminalPhone.getUpdateTime()+"', NULL, "+terminalPhone.getIsDeleted()+");";
                value = "replace INTO `terminal_phone`(`id`,`user_id`, `user_name`, `user_type`, `user_tel`, `card_num`, `imei`, `iccid`, `phone`, `school_id`, `school_name`, `province_id`, `province_name`, `city_id`, `city_name`, `district_id`, `district_name`, `class_id`, `class_name`, `grade_id`, `grade_name`, `create_time`, `update_time`, `delete_time`, `is_deleted`) VALUES "
                        + "("+terminalPhone.getId()+","+terminalPhone.getUserId()+", '"+terminalPhone.getUserName()+"', "+terminalPhone.getUserType()+", '"+terminalPhone.getUserTel()+"', '"+terminalPhone.getCardNum()+"', '"+terminalPhone.getImei()+"', '"+terminalPhone.getIccid()+"', '"+terminalPhone.getPhone()+"', "+terminalPhone.getSchoolId()+", '"+terminalPhone.getSchoolName()+"', "+terminalPhone.getProvinceId()+", '"+terminalPhone.getProvinceName()+"', "+terminalPhone.getCityId()+", '"+terminalPhone.getCityName()+"', "+terminalPhone.getDistrictId()+", '"+terminalPhone.getDistrictName()+"', "+terminalPhone.getClassId()+", '"+terminalPhone.getClassName()+"', "+terminalPhone.getGradeId()+", '"+terminalPhone.getGradeName()+"', '"+terminalPhone.getCreateTime()+"', '"+terminalPhone.getUpdateTime()+"', NULL, "+terminalPhone.getIsDeleted()+");";
            }else {
                value ="UPDATE `hdkt_gps`.`terminal_phone` SET `user_id` = "+str[1]+", `user_name` = '"+str[2]+"', `user_type` = "+str[3]+", `user_tel` = '"+str[4]+"', `card_num` = '"+str[5]+"', `imei` = '"+str[6]+"', `iccid` = '"+str[7]+"', `phone` = '"+str[8]+"', `school_id` = "+str[9]+", `school_name` = '"+str[10]+"', `province_id` = "+str[11]+", `province_name` = '"+str[12]+"', `city_id` = "+str[13]+", `city_name` = '"+str[14]+"', `district_id` = "+str[15]+", `district_name` = '"+str[16]+"', `class_id` = "+str[17]+", `class_name` = '"+str[18]+"', `grade_id` = "+str[19]+", `grade_name` = '"+str[20]+"', `create_time` = '"+str[21]+"', `update_time` = '"+str[22]+"', `delete_time` = NULL, `is_deleted` = "+str[24]+" WHERE `user_id` = "+str[1]+";";
            }
            outStringList.add(value);
        }
        TxtUtil.write(outStringList,outFile);
    }

}
