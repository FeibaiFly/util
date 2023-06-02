package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceSuiteContent;
import com.qtone.util.zxx.entity.PublicResourceSuiteContentExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PublicResourceSuiteContentMapper {
    int countByExample(PublicResourceSuiteContentExample example);

    int deleteByExample(PublicResourceSuiteContentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceSuiteContent record);

    int insertSelective(PublicResourceSuiteContent record);

    List<PublicResourceSuiteContent> selectByExample(PublicResourceSuiteContentExample example);

    PublicResourceSuiteContent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceSuiteContent record, @Param("example") PublicResourceSuiteContentExample example);

    int updateByExample(@Param("record") PublicResourceSuiteContent record, @Param("example") PublicResourceSuiteContentExample example);

    int updateByPrimaryKeySelective(PublicResourceSuiteContent record);

    int updateByPrimaryKey(PublicResourceSuiteContent record);
}