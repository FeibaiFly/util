package com.qtone.util.saveSchool.dto;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

@Data
public class HdktSchool {
    private Integer id;

    private String schoolName;

    private Integer provinceId;

    private String provinceName;

    private Integer cityId;


    private String cityName;

    private Integer districtId;


    private String districtName;

    private Long areaId;

    private String schoolType;

    private Integer section;

    private Integer openPlatform;

    private Integer teachingmaterialNum;

    private String provinceCode;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;

    private Integer isDeleted;
}