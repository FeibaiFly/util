package com.qtone.util.qinqingwang.entity;

import lombok.Data;

import java.util.List;

@Data
public class GSFamilyGroupMember {
    private String MAIN_ACCESS_NUM;
    private String POID_CODE;
    private String POID_LABLE;
    List<GSFamilyGroupMemberDetail> MEMBEREXT_LIST;
}
