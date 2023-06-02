package com.qtone.util.schoolArea.entity;

import org.springframework.util.StringUtils;


public class HjyArea {
    private String id;
    private String code;
    private String name;
    //地区等级，1国家，2省，3市，4区县
    private String rank;
    private String provinceId;
    private String provinceCode;
    private String cityId;
    private String cityCode;

    @Override
    public String toString() {
        return "HjyArea{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        if (!provinceId.equals("-1")) {
            this.provinceId = provinceId;
        }
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        if (!provinceCode.equals("-1")) {
            this.provinceCode = provinceCode;
        }
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        if (!cityId.equals("-1")) {
            this.cityId = cityId;
        }
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        if (!cityCode.equals("-1")) {
            this.cityCode = cityCode;
        }
    }
}
