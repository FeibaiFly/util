package com.qtone.util.qinqingwang.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FamilyPhoneMemberRecord {
    private Integer id;

    private String imeiPhone;

    private Integer schoolId;

    private Integer classId;

    private Integer userId;

    private Integer userType;

    private Integer provinceId;

    private Integer cityId;

    private Integer districtId;

    private String batchNumber;

    private String labelCode;

    private String labelName;

    private String familyPhone;

    private Integer bindType;

    private Integer status;

    private Integer failNum;

    private String failMsg;

    private List<String> familyPhones;


}