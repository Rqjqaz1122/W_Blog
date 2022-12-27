package com.wfit.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (VisitLog)表实体类
 *
 * @author makejava
 * @since 2022-11-30 21:08:30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitLog  {

    private Long id;

    //访客标识码
    private String uuid;
    //请求接口
    private String uri;
    //请求方式
    private String method;
    //请求参数
    private String param;
    //访问行为
    private String behavior;
    //访问内容
    private String content;
    //备注
    private String remark;
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
    //访问时间
    private LocalDateTime createTime;
    //user-agent用户代理
    private String userAgent;


}
