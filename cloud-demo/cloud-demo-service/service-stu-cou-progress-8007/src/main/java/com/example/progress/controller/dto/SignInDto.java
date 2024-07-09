package com.example.progress.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lixx
 * @version 1.0
 * @date 2020-06-10 15:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto implements Serializable {

    /**
     * 表示签到的天数
     */
    private Integer day;

    /**
     * 1表示已经签到, 0表示未签到
     */
    private Integer flag;

    private Integer continueDays;

    private Integer todaySign; //今天是否签到
    // 如果还有其他业务字段就加在后面, 如积分, 如金币

}