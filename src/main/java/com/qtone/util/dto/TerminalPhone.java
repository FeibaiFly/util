package com.qtone.util.dto;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <p>
 * 设备与设备电话号关联表
 * </p>
 *
 * @author guixiong
 * @since 2020-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TerminalPhone extends Model<TerminalPhone> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型：2职工,101车辆
     */
    private Integer userType;

    /**
     * 用户电话号码
     */
    private String userTel;
    /**
     * 卡号
     */
    private String cardNum;

    /**
     * 设备号imei,其他表中的teno字段
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String imei;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String iccid;

    /**
     * 设备号对应的手机号码
     */
    private String phone;

    /**
     * 学校id
     */
    private Integer schoolId;

    /**
     * 学校名称
     */
    private String schoolName;
    //省市区
    private Integer provinceId;
    private String provinceName;
    private Integer cityId;
    private String cityName;
    private Integer districtId;
    private String districtName;
    /**
     * 班级id 扩展字段
     */
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 年级id
     */
    private Integer gradeId;

    /**
     * 年级名称
     */
    private String gradeName;

    /**
     * 带星手机号
     */
    private String userTelMask;
    /**
     * 密码
     */
    private String password;
    /**
     * 卡类型：1电子工卡，2白卡
     */
    private Integer cardType;
    /**
     * 卡状态 1 正常 2 挂失 3 未发卡   4注销
     */
    private Integer cardStatus;
    /**
     * 发卡时间
     */
    private String sendTime;

    //状态(0:正常；1:停用)
    private Integer status;





    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 删除时间
     */
    private String deleteTime;

    /**
     * 0未删除  1已删除
     */
    @TableLogic
    private Integer isDeleted;



    @TableField(exist = false)
    private String departmentName;
}
