package com.wfit.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (OperationLog)表实体类
 * @author makejava
 * @since 2022-11-30 14:47:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class OperationLog  {

    private Long id;

    //操作者用户名
    private String username;
    //请求接口
    private String uri;
    //请求方式
    private String method;
    //请求参数
    private String param;
    //操作描述
    private String description;
    //ip
    private String ip;
    //ip来源
    private String ipSource;
    //操作系统
    private String os;
    //浏览器
    private String browser;
    //请求耗时（毫秒）
    private Long times;
    //操作时间
    private LocalDateTime createTime;
    //user-agent用户代理
    private String userAgent;


}
