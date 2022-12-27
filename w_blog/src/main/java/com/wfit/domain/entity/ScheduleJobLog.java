package com.wfit.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (ScheduleJobLog)表实体类
 *
 * @author makejava
 * @since 2022-12-01 22:42:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJobLog  {
    //任务日志id@TableId
    private Long logId;

    //任务id
    private Long jobId;
    //spring bean名称
    private String beanName;
    //方法名
    private String methodName;
    //参数
    private String params;
    //任务执行结果
    private Boolean status;
    //异常信息
    private String error;
    //耗时（单位：毫秒）
    private Long times;
    //创建时间
    private LocalDateTime createTime;


}
