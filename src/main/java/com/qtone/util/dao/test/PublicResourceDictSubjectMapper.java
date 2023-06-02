package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceDictSubject;
import com.qtone.util.zxx.entity.PublicResourceDictSubjectExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PublicResourceDictSubjectMapper {
    int countByExample(PublicResourceDictSubjectExample example);

    int deleteByExample(PublicResourceDictSubjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceDictSubject record);

    int insertSelective(PublicResourceDictSubject record);

    List<PublicResourceDictSubject> selectByExample(PublicResourceDictSubjectExample example);

    PublicResourceDictSubject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceDictSubject record, @Param("example") PublicResourceDictSubjectExample example);

    int updateByExample(@Param("record") PublicResourceDictSubject record, @Param("example") PublicResourceDictSubjectExample example);

    int updateByPrimaryKeySelective(PublicResourceDictSubject record);

    int updateByPrimaryKey(PublicResourceDictSubject record);
}