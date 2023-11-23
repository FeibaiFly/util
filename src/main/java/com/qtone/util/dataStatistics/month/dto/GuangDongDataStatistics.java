package com.qtone.util.dataStatistics.month.dto;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
  * @Author: zhangpk
  * @Description:
  * @Date:Created in 10:50 2023/6/6
  * @Modified By:
*/
@Data
public class GuangDongDataStatistics {

    private Integer month;

    private Integer provinceId;
    /**
     * 省
     */
    private String provinceName;

    private Integer cityId;
    /**
     * 市
     */
    private String cityName;

    private Integer districtId;
    /**
     * 区
     */
    private String districtName;

    /**
     * 学校ID
     */
    private Integer schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 总学生数
     */
    private Integer totalStudentNum=0;

    /**
     * 新增学生数
     */
    private Integer studentNum=0;

    /**
     * 总绑卡数
     */
    private Integer totalBindNum=0;

    /**
     * 新增绑卡数
     */
    private Integer bindNum=0;

    /**
     * 总通话次数
     */
    private Integer totalCallNum=0;

    /**
     * 新增通话次数
     */
    private Integer callNum=0;

    /**
     * 通话用户数
     */
    private Integer callUserNum=0;

    /**
     * 总通话时长
     */
    private Integer totalCallDuration=0;

    /**
     * 新增通话时长
     */
    private Integer callDuration=0;

    /**
     * 总家长请求定位次数
     */
    private Integer totalRequestLocationNum=0;
    /**
     * 新增家长请求定位次数
     */
    private Integer requestLocationNum=0;
    /**
     * 新增家长请求定位用户数
     */
    private Integer requestLocationUserNum=0;
    /**
     * 总SOS告警发起次数
     */
    private Integer totalSosAlarmNum=0;
    /**
     * 新增SOS告警发起次数
     */
    private Integer sosAlarmNum=0;
    /**
     * 新增SOS告警用户数
     */
    private Integer sosAlarmUserNum=0;

    /**
     * 总围栏触发报警次数
     */
    private Integer totalRegionalAlarmNum=0;
    /**
     * 新增围栏触发报警次数
     */
    private Integer regionalAlarmNum=0;

    /**
     * 新增设备上报定位次数
     */
    private Integer reportLocationNum=0;

    /**
     * 新增解绑数
     */
    private Integer unBindNum=0;

    /**
     * 解绑率=解绑数/（现有绑卡数+解绑数）
     */
    private String bindRate="0%";

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        GuangDongDataStatistics that = (GuangDongDataStatistics) o;
        return Objects.equals(schoolId, that.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId);
    }
}