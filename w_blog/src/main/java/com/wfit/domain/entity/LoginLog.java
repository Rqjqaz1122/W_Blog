package com.wfit.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class LoginLog {

    private Long id;

    private String username;//用户名称

    private String ip;//ip

    private String ipSource;//ip来源

    private String os;//操作系统

    private String browser;//浏览器

    private Boolean status;//登录状态

    private String description;//操作信息

    private LocalDateTime createTime;//操作时间

    private String userAgent;

}
