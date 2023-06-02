package com.qtone.util.dao.test;

import com.qtone.util.schoolArea.entity.DictSchoolArea;
import com.qtone.util.schoolArea.entity.DictSchoolAreaExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface DictSchoolAreaMapper {
    int countByExample(DictSchoolAreaExample example);

    int deleteByExample(DictSchoolAreaExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DictSchoolArea record);

    int insertSelective(DictSchoolArea record);

    List<DictSchoolArea> selectByExample(DictSchoolAreaExample example);

    DictSchoolArea selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DictSchoolArea record, @Param("example") DictSchoolAreaExample example);

    int updateByExample(@Param("record") DictSchoolArea record, @Param("example") DictSchoolAreaExample example);

    int updateByPrimaryKeySelective(DictSchoolArea record);

    int updateByPrimaryKey(DictSchoolArea record);
}