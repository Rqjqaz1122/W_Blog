package com.wfit.controller.admin;

import com.wfit.domain.Result;
import com.wfit.domain.entity.ScheduleJob;
import com.wfit.service.ScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminScheduleJobController {


    @Autowired
    private ScheduleJobService scheduleJobService;

    @RequestMapping("/jobs")
    public Result jobs(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return scheduleJobService.getJobs(pageNum,pageSize);
    }

    @RequestMapping(value = "/job",method = RequestMethod.PUT)
    public Result updateJob(@RequestBody ScheduleJob scheduleJob){

        return scheduleJobService.updateJob(scheduleJob);
    }

    @RequestMapping(value = "/job/status",method = RequestMethod.PUT)
    public Result jobStatus(@RequestParam("jobId") Long jobId,
                            @RequestParam("status") Boolean status){


        return scheduleJobService.jobStatus(jobId,status);
    }

    @RequestMapping(value = "/job/run",method = RequestMethod.POST)
    public Result jobRun(Long jobId){

        return scheduleJobService.jobRun(jobId);
    }

    @RequestMapping("/job/logs")
    public Result jobLogs(@RequestParam(value = "date",defaultValue = "") String[] date,
                          @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){


        return scheduleJobService.jobLogs(date,pageNum,pageSize);
    }

    @RequestMapping(value = "/job",method = RequestMethod.DELETE)
    public Result deleteJob(@RequestParam("jobId") Long jobId){

        return scheduleJobService.deleteJobById(jobId);
    }

    @RequestMapping(value = "/job/log",method = RequestMethod.DELETE)
    public Result deleteJobLog(@RequestParam("logId") Long logId){

        return scheduleJobService.deleteJobLogById(logId);
    }


}
