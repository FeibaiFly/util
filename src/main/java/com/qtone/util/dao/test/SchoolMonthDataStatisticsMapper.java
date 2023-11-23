package com.qtone.util.dao.test;

import com.qtone.util.dataStatistics.month.dto.GuangDongDataStatistics;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 17:07 2023/6/7
 * @Modified By:
 */
public interface SchoolMonthDataStatisticsMapper {
    int deleteByMonth(@Param("month") Integer month);

    int insertData(GuangDongDataStatistics record);

    GuangDongDataStatistics getByCityIdMonth(@Param("month") Integer month,@Param("cityId") Integer cityId);
}