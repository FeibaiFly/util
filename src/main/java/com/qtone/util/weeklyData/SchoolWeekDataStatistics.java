package com.qtone.util.weeklyData;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.Objects;

import lombok.Data;

/**
  * @Author: zhangpk
  * @Description:
  * @Date:Created in 10:50 2023/6/6
  * @Modified By:
*/
@Data
@TableName(value = "SchoolWeekDataStatistics")
public class SchoolWeekDataStatistics {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 省
     */
    @TableField(value = "province_name")
    private String provinceName;

    /**
     * 市
     */
    @TableField(value = "city_name")
    private String cityName;

    /**
     * 区
     */
    @TableField(value = "district_name")
    private String districtName;

    /**
     * 学校ID
     */
    @TableField(value = "school_id")
    private Integer schoolId;

    /**
     * 学校名称
     */
    @TableField(value = "school_name")
    private String schoolName;

    /**
     * 总学生数
     */
    @TableField(value = "total_student_num")
    private Integer totalStudentNum=0;

    /**
     * 新增学生数
     */
    @TableField(value = "student_num")
    private Integer studentNum=0;

    /**
     * 总绑卡数
     */
    @TableField(value = "total_bind_num")
    private Integer totalBindNum=0;

    /**
     * 新增绑卡数
     */
    @TableField(value = "bind_num")
    private Integer bindNum=0;

    /**
     * 总通话次数
     */
    @TableField(value = "total_call_num")
    private Integer totalCallNum=0;

    /**
     * 新增通话次数
     */
    @TableField(value = "call_num")
    private Integer callNum=0;

    /**
     * 总通话时长
     */
    @TableField(value = "total_call_duration")
    private Integer totalCallDuration=0;

    /**
     * 新增通话时长
     */
    @TableField(value = "call_duration")
    private Integer callDuration=0;

    /**
     * 新增设备上报定位次数
     */
    @TableField(value = "report_location_num")
    private Integer reportLocationNum=0;

    /**
     * 新增家长请求定位次数
     */
    @TableField(value = "request_location_num")
    private Integer requestLocationNum=0;

    /**
     * 新增SOS告警发起次数
     */
    @TableField(value = "sos_alarm_num")
    private Integer sosAlarmNum=0;

    /**
     * 新增围栏触发报警次数
     */
    @TableField(value = "regional_alarm_num")
    private Integer regionalAlarmNum=0;

    /**
     * 新增解绑数
     */
    @TableField(value = "un_bind_num")
    private Integer unBindNum=0;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        SchoolWeekDataStatistics that = (SchoolWeekDataStatistics) o;
        return Objects.equals(schoolId, that.schoolId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schoolId);
    }
}