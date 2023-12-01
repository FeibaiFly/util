package com.qtone.util.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 学生打卡记录
 * </p>
 *
 * @author lijiao
 * @since 2020-03-03
 */
@Data
public class KqWorkCheckInStudentRecord {

    private String id;

    /**
     * 班级id
     */
    private Integer classId;

    private String className;

    /**
     * 年级id
     */
    private Integer gradeId;

    private String gradeName;


    /**
     * 设备id
     */
    private Integer deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 位置ID
     */
    private Integer devicePositionId;

    /**
     * 设备位置
     */
    private String devicePosition;

}
