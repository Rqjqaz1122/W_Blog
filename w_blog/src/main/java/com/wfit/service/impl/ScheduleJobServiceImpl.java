package com.wfit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.domain.Result;
import com.wfit.domain.entity.ScheduleJob;
import com.wfit.domain.entity.ScheduleJobLog;
import com.wfit.mapper.ScheduleJobMapper;
import com.wfit.quartz.ScheduleUtils;
import com.wfit.service.ScheduleJobService;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    @PostConstruct
    public void init() {
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectJobs();
        for (ScheduleJob scheduleJob : scheduleJobs) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            //如果不存在，则创建
            if(cronTrigger == null){
                ScheduleUtils.createScheduleJob(scheduler,scheduleJob);
            }else{
                ScheduleUtils.updateScheduleJob(scheduler,scheduleJob);
            }
        }
    }


    @Override
    public Result getJobs(Integer pageNum, Integer pageSize) {

        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectJobs();
        PageInfo<ScheduleJob> pageInfo = new PageInfo<ScheduleJob>(scheduleJobs);

        return Result.ok("请求成功",pageInfo);
    }

    @Override
    @Transactional
    public Result jobStatus(Long jobId, Boolean status) {

        if(status){
            ScheduleUtils.resumeJob(scheduler,jobId);
        }else{
            ScheduleUtils.pauseJob(scheduler,jobId);
        }

        scheduleJobMapper.updateJobStatus(jobId,status);

        return Result.ok("修改成功");
    }

    @Override
    public Result jobRun(Long jobId) {

        ScheduleUtils.nowRun(scheduler,scheduleJobMapper.selectJobById(jobId));

        return Result.ok("执行完毕");
    }

    @Override
    public Result updateJob(ScheduleJob scheduleJob) {

        Integer i = scheduleJobMapper.updateJob(scheduleJob);

        if(i < 1){
            throw new RuntimeException("修改失败");
        }

        return Result.ok("修改成功");
    }

    @Override
    public void saveJobLog(ScheduleJobLog scheduleJobLog) {

        Integer i = scheduleJobMapper.saveJobLog(scheduleJobLog);

        if(i < 1){
            throw new RuntimeException("保存日志失败");
        }
    }

    @Override
    public Result jobLogs(String[] date, Integer pageNum, Integer pageSize) {

        String beginTime = null;
        String endTime = null;

        if(date.length == 2){
            beginTime = date[0];
            endTime = date[1];
        }

        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<ScheduleJobLog> scheduleJobLogs = scheduleJobMapper.selectJobLogs();
        PageInfo<ScheduleJobLog> pageInfo = new PageInfo<ScheduleJobLog>(scheduleJobLogs);

        return Result.ok("请求成功",pageInfo);
    }

    @Override
    public Result deleteJobById(Long jobId) {

        ScheduleUtils.deleteScheduleJob(scheduler,jobId);

        Integer i = scheduleJobMapper.deleteJobById(jobId);

        if(i < 0){
            throw new RuntimeException("删除定时任务失败");
        }

        return Result.ok("删除成功");
    }

    @Override
    public Result deleteJobLogById(Long logId) {

        Integer i = scheduleJobMapper.deleteJobLogById(logId);

        if(i < 1){
            throw new RuntimeException("删除定时任务日志失败");
        }

        return Result.ok("操作成功");
    }
}
