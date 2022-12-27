package com.wfit.mapper;

import com.wfit.domain.entity.ScheduleJob;
import com.wfit.domain.entity.ScheduleJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ScheduleJobMapper {
    /**
     * 获取所有定时任务
     * @return
     */
    List<ScheduleJob> selectJobs();

    /**
     * 修改定时任务状态
     * @param jobId
     * @param status
     */
    void updateJobStatus(@Param("jobId") Long jobId,@Param("status") Boolean status);

    /**
     * 修改任务
     * @param scheduleJob
     * @return
     */
    Integer updateJob(ScheduleJob scheduleJob);

    /**
     * 记录定时任务执行日志
     * @param scheduleJobLog
     * @return
     */
    Integer saveJobLog(ScheduleJobLog scheduleJobLog);

    /**
     * 根据id查询定时任务
     * @param jobId
     * @return
     */
    ScheduleJob selectJobById(Long jobId);

    /**
     * 查询执行定时任务的日志
     * @return
     */
    List<ScheduleJobLog> selectJobLogs();

    /**
     * 根据id删除定时任务
     * @param jobId
     * @return
     */
    Integer deleteJobById(Long jobId);

    /**
     * 根据id删除定时任务
     * @param logId
     * @return
     */
    Integer deleteJobLogById(Long logId);
}
