package com.qtone.util.dao.prod;

import com.qtone.util.dto.TerminalPhone;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zhangpk
 * @Description:
 * @Date:Created in 10:23 2024/1/11
 * @Modified By:
 */
@Mapper
public interface ProdGpsMapper {
    List<TerminalPhone> getTerminalPhoneList(@Param("provinceId") Integer provinceId,@Param("updateTime")String updateTime);
}
