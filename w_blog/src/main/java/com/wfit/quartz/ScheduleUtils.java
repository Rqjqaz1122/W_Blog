package com.wfit.quartz;

import com.wfit.domain.entity.ScheduleJob;
import org.quartz.*;

public class ScheduleUtils {

    private static final String JOB_NAME = "TASK_";

    public static TriggerKey getTriggerKey(Long jobId){
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    public static JobKey getJobKey(Long jobId){
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    public static CronTrigger getCronTrigger(Scheduler scheduler,Long jobId){
        try{
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        }catch (SchedulerException e){
            throw new RuntimeException("获取定时任务CronTrigger出现异常",e);
        }
    }

    public static void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob){

        try{
            JobDetail jobDetail = JobBuilder.newJob(ScheduleTaskJob.class).withIdentity(getJobKey(scheduleJob.getJobId())).build();
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron()).withMisfireHandlingInstructionDoNothing();
            CronTrigger build = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getJobId())).withSchedule(cronScheduleBuilder).build();
            jobDetail.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY,scheduleJob);
            scheduler.scheduleJob(jobDetail,build);
            if(!scheduleJob.getStatus()){
                pauseJob(scheduler,scheduleJob.getJobId());
            }
        }catch (SchedulerException e){
            throw new RuntimeException("更新定时任务失败",e);
        }

    }

    public static void updateScheduleJob(Scheduler scheduler,ScheduleJob scheduleJob){
        try{
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getJobId());

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron()).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = getCronTrigger(scheduler, scheduleJob.getJobId());
            cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            cronTrigger.getJobDataMap().put(ScheduleJob.JOB_PARAM_KEY,scheduleJob);
            scheduler.rescheduleJob(triggerKey,cronTrigger);

            if(!scheduleJob.getStatus()){
                pauseJob(scheduler,scheduleJob.getJobId());
            }
        }catch (SchedulerException e){
            throw new RuntimeException("更新定时任务失败",e);
        }
    }

    public static void nowRun(Scheduler scheduler,ScheduleJob scheduleJob){

        try{
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(ScheduleJob.JOB_PARAM_KEY,scheduleJob);
            scheduler.triggerJob(getJobKey(scheduleJob.getJobId()),dataMap);
        }catch (SchedulerException e){
            throw new RuntimeException("立即执行定时任务失败",e);
        }
    }


    public static void pauseJob(Scheduler scheduler, Long jobId) {

        try{
            scheduler.pauseJob(getJobKey(jobId));
        }catch (SchedulerException e){
            throw new RuntimeException("暂停定时任务失败",e);
        }
    }

    public static void resumeJob(Scheduler scheduler,Long jobId){
        try{
            scheduler.resumeJob(getJobKey(jobId));
        }catch (SchedulerException e){
            throw new RuntimeException("恢复定时任务失败",e);
        }
    }

    public static void deleteScheduleJob(Scheduler scheduler,Long jobId){
        try{
            scheduler.deleteJob(getJobKey(jobId));
        }catch (SchedulerException e){
            throw new RuntimeException("删除定时任务失败",e);
        }
    }


}
