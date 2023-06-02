package com.qtone.util.dao.test;

import com.qtone.util.zxx.entity.PublicResourceDictChapter;
import com.qtone.util.zxx.entity.Unitinfo;
import com.qtone.util.zxx.entity.UnitinfoExample;
import com.qtone.util.zxx.model.ZxxId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface UnitinfoMapper {
    int countByExample(UnitinfoExample example);

    int deleteByExample(UnitinfoExample example);

    int deleteByPrimaryKey(String unitId);

    int insert(Unitinfo record);

    int insertSelective(Unitinfo record);

    List<Unitinfo> selectByExample(UnitinfoExample example);

    Unitinfo selectByPrimaryKey(String unitId);

    int updateByExampleSelective(@Param("record") Unitinfo record, @Param("example") UnitinfoExample example);

    int updateByExample(@Param("record") Unitinfo record, @Param("example") UnitinfoExample example);

    int updateByPrimaryKeySelective(Unitinfo record);

    int updateByPrimaryKey(Unitinfo record);

    List<ZxxId> getZxxIdInfo();

    List<String> getUnitId(@Param("gradeLevelId") String gradeLevelId,@Param("subjectId") String subjectId,
            @Param("materialId") String materialId,@Param("gradeId") String gradeId);

}