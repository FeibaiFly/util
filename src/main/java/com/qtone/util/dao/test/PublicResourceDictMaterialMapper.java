package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceDictMaterial;
import com.qtone.util.zxx.entity.PublicResourceDictMaterialExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface PublicResourceDictMaterialMapper {
    int countByExample(PublicResourceDictMaterialExample example);

    int deleteByExample(PublicResourceDictMaterialExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceDictMaterial record);

    int insertSelective(PublicResourceDictMaterial record);

    List<PublicResourceDictMaterial> selectByExample(PublicResourceDictMaterialExample example);

    PublicResourceDictMaterial selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceDictMaterial record, @Param("example") PublicResourceDictMaterialExample example);

    int updateByExample(@Param("record") PublicResourceDictMaterial record, @Param("example") PublicResourceDictMaterialExample example);

    int updateByPrimaryKeySelective(PublicResourceDictMaterial record);

    int updateByPrimaryKey(PublicResourceDictMaterial record);
}