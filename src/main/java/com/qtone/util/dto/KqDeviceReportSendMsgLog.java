package com.qtone.util.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;


/**
 * 考勤记录上报日志
 *
 * @Author yangfei
 * @Date 2020/12/03 18:13
 * @Description
 */
@Data
public class KqDeviceReportSendMsgLog {


    // 学校id
    private Integer schoolId;

    // 学校名称
    private String schoolName;

    // 关联用户(学生)id
    private String relationUserId;

    // 关联用户(学生)name
    private String relationUserName;

    private String notifyUserName;  //短信or微信通知人姓名

    private String notifyUserTel;   //短信or微信通知人手机号

    // 下发时间
    private String sendTime;

    // 消息通道
    private String sendWayValue;

    // 消息标签
    private String msgTagValue;

    // 发送结果
    private String rstStatusValue;
    //发送内容
    private String sendMsg;


}
