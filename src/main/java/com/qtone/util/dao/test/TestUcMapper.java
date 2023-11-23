package com.qtone.util.dao.test;

import com.qtone.util.dto.ThirdStudentCardInfo;
import com.qtone.util.qinqingwang.entity.FamilyPhoneMemberRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestUcMapper {
    List<FamilyPhoneMemberRecord> getFamilyPhoneMemberRecord(@Param("batchNumber") String batch_number);

    void insertFamilyPhoneMemberRecord(@Param("imeiPhone") String imeiPhone,@Param("schoolId") Integer schoolId,
                                       @Param("classId") Integer classId, @Param("userId") Integer userId,
                                       @Param("userType") Integer userType,@Param("provinceId") Integer provinceId,
                                       @Param("cityId") Integer cityId, @Param("districtId") Integer districtId,
                                       @Param("batchNumber") String batchNumber,@Param("labelCode") String labelCode,
                                       @Param("labelName") String labelName, @Param("familyPhone") String familyPhone,
                                       @Param("bindType") Integer bindType,@Param("status") Integer status,
                                        @Param("failMsg") String failMsg);

    String getOrderGtid(@Param("gtid") String gtid);

    void insertOrderRequest(@Param("gtid") String gtid,@Param("paramString") String paramString);

    List<ThirdStudentCardInfo> getStudentCardInfo(@Param("schoolId") Integer schoolId);
}