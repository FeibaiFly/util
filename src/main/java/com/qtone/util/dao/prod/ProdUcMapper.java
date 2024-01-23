package com.qtone.util.dao.prod;

import com.qtone.util.dataStatistics.month.dto.GuangDongDataStatistics;
import com.qtone.util.dataStatistics.month.dto.SchoolUserDto;
import com.qtone.util.dataStatistics.month.dto.StudentCardInfo;
import com.qtone.util.dataStatistics.week.dto.AttendanceInfo;
import com.qtone.util.dataStatistics.week.dto.SXBindInfo;
import com.qtone.util.dto.*;
import com.qtone.util.qinqingwang.entity.FamilyPhoneMemberRecord;
import com.qtone.util.saveSchool.dto.HdktSchool;
import com.qtone.util.saveSchool.dto.SchoolArea;
import com.qtone.util.dataStatistics.week.dto.SchoolWeekDataStatistics;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProdUcMapper {
    List<FamilyPhoneMemberRecord> getBindImeiPhone(@Param("list") List<String> imeiPhones);

    List<SchoolArea> getSchoolAreaByParentId(@Param("parentId") Integer parentId);

    HdktSchool getSchoolByName(@Param("schoolName") String schoolName, @Param("provinceId") Integer provinceId);

    Integer getAddBindNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer getAnswerNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<SchoolWeekDataStatistics> getSchoolBindInfo(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<SchoolWeekDataStatistics> getSchoolUnBindNumByStudetnId(@Param("list") List<Integer> studentIds);


    List<GuangDongDataStatistics> getSchoolBindInfoByProvinceId(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("provinceId") Integer provinceId);

    List<SchoolUserDto> getSchoolUserNumByUserId(@Param("list") List<Integer> userIds);

    List<StudentCardInfo> getStudentCardInfoByCityId(@Param("cityId") Integer cityId);

    ThirdUserId getThirdUserIdByUserId(@Param("userId") String userId);

    List<SXBindInfo> getSXBindInfo();

    Integer getStudentIdByImei(@Param("imei") String imei);

    List<AttendanceInfo> getAttendanceInfo(@Param("provinceId") Integer provinceId);

    List<TerminalPhone> getTerminalPhoneListBySchoolId(@Param("schoolIds") Integer[] schoolIds);

    Integer getStudentNum(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("provinceId") Integer provinceId);

    List<SchoolKqInfo> getSchoolList(@Param("provinceId") Integer provinceId);

    List<Integer> getStudentIdByClassId(@Param("classId") Integer classId);

    List<ImeiAreaInfo> getBindImei(@Param("provinceId") Integer provinceId);

    List<SchoolDeviceInfo> getImeiInfoByCityId(@Param("cityIds") List<Integer> cityIds);

    List<StudentInfo> getStudentNameById(@Param("studentIds") List<Integer> studentIds);

    List<StudentInfo> getStudentIdBySchoolId(@Param("schoolId") Integer schoolId);

    List<HistoryImeiInfo> getStudentBindInfo(@Param("provinceId") Integer provinceId,@Param("isBind") Integer isBind);

}
