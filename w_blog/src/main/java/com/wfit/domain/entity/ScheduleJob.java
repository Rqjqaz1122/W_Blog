package com.wfit.domain.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (ScheduleJob)表实体类
 *
 * @author makejava
 * @since 2022-12-01 22:42:01
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleJob  {
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    //任务id@TableId
    private Long jobId;

    //spring bean名称
    private String beanName;
    //方法名
    private String methodName;
    //参数
    private String params;
    //cron表达式
    private String cron;
    //任务状态
    private Boolean status;
    //备注
    private String remark;
    //创建时间
    private Date createTime;


}
