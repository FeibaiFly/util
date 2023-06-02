package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceDictGrade;
import com.qtone.util.zxx.entity.PublicResourceDictGradeExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PublicResourceDictGradeMapper {
    int countByExample(PublicResourceDictGradeExample example);

    int deleteByExample(PublicResourceDictGradeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceDictGrade record);

    int insertSelective(PublicResourceDictGrade record);

    List<PublicResourceDictGrade> selectByExample(PublicResourceDictGradeExample example);

    PublicResourceDictGrade selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceDictGrade record, @Param("example") PublicResourceDictGradeExample example);

    int updateByExample(@Param("record") PublicResourceDictGrade record, @Param("example") PublicResourceDictGradeExample example);

    int updateByPrimaryKeySelective(PublicResourceDictGrade record);

    int updateByPrimaryKey(PublicResourceDictGrade record);
}