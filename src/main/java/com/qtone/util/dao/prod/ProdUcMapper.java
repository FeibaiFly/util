package com.qtone.util.dao.prod;

import com.qtone.util.qinqingwang.entity.FamilyPhoneMemberRecord;
import com.qtone.util.saveSchool.dto.HdktSchool;
import com.qtone.util.saveSchool.dto.SchoolArea;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdUcMapper {
    List<FamilyPhoneMemberRecord> getBindImeiPhone(@Param("list") List<String> imeiPhones);

    List<SchoolArea> getSchoolAreaByParentId(@Param("parentId") Integer parentId);

    HdktSchool getSchoolByName(@Param("schoolName") String schoolName,@Param("provinceId") Integer provinceId);

    Integer getAddBindNum(@Param("startTime")String startTime,@Param("endTime")String endTime);

    Integer getAnswerNum(@Param("startTime")String startTime,@Param("endTime")String endTime);

}