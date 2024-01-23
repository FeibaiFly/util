package com.qtone.util.dto;

import lombok.Data;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 11:37 2024/1/23
 * @Modified By:
 */
@Data
public class UserLocationInfo {
    private Integer userId;
    private Integer schoolId;
    private Integer locationNum;

    @Override
    public String toString() {
        return userId+","+schoolId+","+locationNum;
    }
}
