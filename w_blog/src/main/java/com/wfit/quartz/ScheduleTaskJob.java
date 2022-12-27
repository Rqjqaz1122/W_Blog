package com.wfit.quartz;

import com.wfit.domain.entity.ScheduleJob;
import com.wfit.domain.entity.ScheduleJobLog;
import com.wfit.service.ScheduleJobService;
import com.wfit.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
public class ScheduleTaskJob extends QuartzJobBean {

    private static final ExecutorService EXECUTOR_SERVICE  = Executors.newSingleThreadExecutor();

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get(ScheduleJob.JOB_PARAM_KEY);

        ScheduleJobService scheduleJobService = (ScheduleJobService) SpringContextUtils.getBean("scheduleJobServiceImpl");

        //数据库保存任务执行记录
        ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
        scheduleJobLog.setJobId(scheduleJob.getJobId());
        scheduleJobLog.setBeanName(scheduleJob.getBeanName());
        scheduleJobLog.setMethodName(scheduleJob.getMethodName());
        scheduleJobLog.setParams(scheduleJob.getParams());
        scheduleJobLog.setCreateTime(LocalDateTime.now());
        //任务开始时间
        long beginTime = System.currentTimeMillis();
        log.info("任务准备执行，任务ID :{}",scheduleJob.getJobId());
        try{
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),scheduleJob.getMethodName(),scheduleJob.getParams());
            Future<?> submit = EXECUTOR_SERVICE.submit(task);
            submit.get();
            long times = System.currentTimeMillis() - beginTime;
            scheduleJobLog.setTimes(times);
            scheduleJobLog.setStatus(true);
            log.info("任务执行成功，任务ID:{},总共耗时: {}",scheduleJob.getJobId(),times);
        }catch (Exception e){
            long times = System.currentTimeMillis() - beginTime;
            scheduleJobLog.setTimes(times);
            scheduleJobLog.setStatus(false);
            scheduleJobLog.setError(e.toString());
            log.error("任务执行失败，任务ID: {}",scheduleJob.getJobId(),e);
        }finally {
            scheduleJobService.saveJobLog(scheduleJobLog);
        }

    }
}
