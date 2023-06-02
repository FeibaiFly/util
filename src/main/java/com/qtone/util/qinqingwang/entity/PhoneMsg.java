package com.qtone.util.qinqingwang.entity;

public class PhoneMsg {
    private String imeiPhone;
    private String msg;
    private String familyPhone;

    public PhoneMsg(String imeiPhone, String msg) {
        this.imeiPhone = imeiPhone;
        this.msg = msg;
    }

    public PhoneMsg(String imeiPhone, String msg, String familyPhone) {
        this.imeiPhone = imeiPhone;
        this.msg = msg;
        this.familyPhone = familyPhone;
    }

    public String getImeiPhone() {
        return imeiPhone;
    }

    public void setImeiPhone(String imeiPhone) {
        this.imeiPhone = imeiPhone;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFamilyPhone() {
        return familyPhone;
    }

    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }
}
