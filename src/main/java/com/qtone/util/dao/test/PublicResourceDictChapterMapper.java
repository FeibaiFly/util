package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceDictChapter;
import com.qtone.util.zxx.entity.PublicResourceDictChapterExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PublicResourceDictChapterMapper {
    int countByExample(PublicResourceDictChapterExample example);

    int deleteByExample(PublicResourceDictChapterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PublicResourceDictChapter record);

    int insertSelective(PublicResourceDictChapter record);

    List<PublicResourceDictChapter> selectByExample(PublicResourceDictChapterExample example);

    PublicResourceDictChapter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PublicResourceDictChapter record, @Param("example") PublicResourceDictChapterExample example);

    int updateByExample(@Param("record") PublicResourceDictChapter record, @Param("example") PublicResourceDictChapterExample example);

    int updateByPrimaryKeySelective(PublicResourceDictChapter record);

    int updateByPrimaryKey(PublicResourceDictChapter record);

    List<PublicResourceDictChapter> getLastChapter();

    void updateZxChapterNameById(@Param("zwChapterName") String zwChapterName,@Param("id") Integer id);
}