package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 16:21 2023/10/27
 * @Modified By:
 */
@Data
public class SchoolKqInfo {
    private Integer schoolId;
    private String schoolName;
    private String provinceName;
    private String cityName;
    private String districtName;
    private String day;
    private Integer kqNum;
}
