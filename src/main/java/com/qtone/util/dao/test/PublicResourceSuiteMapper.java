package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceSuite;
import com.qtone.util.zxx.entity.PublicResourceSuiteExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PublicResourceSuiteMapper {
    int countByExample(PublicResourceSuiteExample example);

    int deleteByExample(PublicResourceSuiteExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceSuite record);

    int insertSelective(PublicResourceSuite record);

    List<PublicResourceSuite> selectByExample(PublicResourceSuiteExample example);

    PublicResourceSuite selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceSuite record, @Param("example") PublicResourceSuiteExample example);

    int updateByExample(@Param("record") PublicResourceSuite record, @Param("example") PublicResourceSuiteExample example);

    int updateByPrimaryKeySelective(PublicResourceSuite record);

    int updateByPrimaryKey(PublicResourceSuite record);
}