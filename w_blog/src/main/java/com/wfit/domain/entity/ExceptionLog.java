package com.wfit.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (ExceptionLog)表实体类
 *
 * @author makejava
 * @since 2022-11-30 17:21:42
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionLog  {

    private Long id;

    //请求接口
    private String uri;
    //请求方式
    private String method;
    //请求参数
    private String param;
    //操作描述
    private String description;
    //异常信息
    private String error;
    //ip
    private String ip;
    //ip来源
    private String ipSource;
    //操作系统
    private String os;
    //浏览器
    private String browser;
    //操作时间
    private LocalDateTime createTime;
    //user-agent用户代理
    private String userAgent;


}
