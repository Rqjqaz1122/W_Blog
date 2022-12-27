package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.entity.ScheduleJob;
import com.wfit.domain.entity.ScheduleJobLog;

public interface ScheduleJobService {
    /**
     * 获取所有定时任务
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result getJobs(Integer pageNum, Integer pageSize);

    /**
     * 修改定时任务状态
     * @param jobId
     * @param status
     * @return
     */
    Result jobStatus(Long jobId, Boolean status);

    /**
     * 执行一次任务
     * @param jobId
     * @return
     */
    Result jobRun(Long jobId);

    /**
     * 修改任务
     * @param scheduleJob
     * @return
     */
    Result updateJob(ScheduleJob scheduleJob);

    /**
     * 记录定时任务日志
     * @param scheduleJobLog
     */
    void saveJobLog(ScheduleJobLog scheduleJobLog);

    /**
     * 查询日志
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result jobLogs(String[] date, Integer pageNum, Integer pageSize);

    /**
     * 根据id删除定时任务
     * @param jobId
     * @return
     */
    Result deleteJobById(Long jobId);

    /**
     * 根据id删除定时任务日志
     * @param logId
     * @return
     */
    Result deleteJobLogById(Long logId);
}
