package com.qtone.util.dao.test;

import com.qtone.util.dataStatistics.week.dto.SchoolWeekDataStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 10:50 2023/6/6
 * @Modified By:
 */
@Mapper
public interface SchoolWeekDataStatisticsMapper {
    SchoolWeekDataStatistics getBySchoolId(@Param("schoolId") Integer schoolId);

    int insertData(SchoolWeekDataStatistics record);

    int updateByPrimaryKey(SchoolWeekDataStatistics record);

    int incrTotalCallInfo(@Param("schoolId")Integer schoolId,@Param("callNum")Integer callNum,@Param("callDuration")Integer callDuration);
}