package com.wfit.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Visitor)表实体类
 *
 * @author makejava
 * @since 2022-11-30 21:44:44
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Visitor  {

    private Long id;

    //访客标识码
    private String uuid;
    //ip
    private String ip;
    //ip来源
    private String ipSource;
    //操作系统
    private String os;
    //浏览器
    private String browser;
    //首次访问时间
    private LocalDateTime createTime;
    //最后访问时间
    private LocalDateTime lastTime;
    //访问页数统计
    private Integer pv;
    //user-agent用户代理
    private String userAgent;


}
