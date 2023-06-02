package com.qtone.util.dao.test;

import com.qtone.util.schoolArea.entity.DictHjySchoolArea;
import com.qtone.util.schoolArea.entity.DictHjySchoolAreaExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DictHjySchoolAreaMapper {
    int countByExample(DictHjySchoolAreaExample example);

    int deleteByExample(DictHjySchoolAreaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DictHjySchoolArea record);

    int insertSelective(DictHjySchoolArea record);

    List<DictHjySchoolArea> selectByExample(DictHjySchoolAreaExample example);

    DictHjySchoolArea selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DictHjySchoolArea record, @Param("example") DictHjySchoolAreaExample example);

    int updateByExample(@Param("record") DictHjySchoolArea record, @Param("example") DictHjySchoolAreaExample example);

    int updateByPrimaryKeySelective(DictHjySchoolArea record);

    int updateByPrimaryKey(DictHjySchoolArea record);
}