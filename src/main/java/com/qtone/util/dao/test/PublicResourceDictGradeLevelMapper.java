package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceDictGradeLevel;
import com.qtone.util.zxx.entity.PublicResourceDictGradeLevelExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PublicResourceDictGradeLevelMapper {
    int countByExample(PublicResourceDictGradeLevelExample example);

    int deleteByExample(PublicResourceDictGradeLevelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceDictGradeLevel record);

    int insertSelective(PublicResourceDictGradeLevel record);

    List<PublicResourceDictGradeLevel> selectByExample(PublicResourceDictGradeLevelExample example);

    PublicResourceDictGradeLevel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceDictGradeLevel record, @Param("example") PublicResourceDictGradeLevelExample example);

    int updateByExample(@Param("record") PublicResourceDictGradeLevel record, @Param("example") PublicResourceDictGradeLevelExample example);

    int updateByPrimaryKeySelective(PublicResourceDictGradeLevel record);

    int updateByPrimaryKey(PublicResourceDictGradeLevel record);
}