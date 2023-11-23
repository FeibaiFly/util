package com.qtone.util.dataStatistics.week.dto;

import lombok.Data;

import java.util.Objects;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 14:28 2023/7/21
 * @Modified By:
 */
@Data
public class StudentAttendanceInfo {
    private Integer schoolId;
    private Integer userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentAttendanceInfo that = (StudentAttendanceInfo) o;
        return Objects.equals(schoolId, that.schoolId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId, userId);
    }
}
