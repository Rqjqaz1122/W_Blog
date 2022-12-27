package com.wfit.controller.admin;

import com.wfit.domain.Result;
import com.wfit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminVisitController {


    @Autowired
    private VisitService visitService;

    @RequestMapping("/visitLogs")
    public Result visitLogs(@RequestParam(value = "uuid",defaultValue = "") String uuid,
                            @RequestParam(value = "date",defaultValue = "") String[] date,
                            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                            @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){
        
        return visitService.visitLogs(uuid,date,pageNum,pageSize);
    }

    @RequestMapping("/visitors")
    public Result visitorsLogs(@RequestParam(value = "date",defaultValue = "") String[] date,
                               @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return visitService.visitorsLogs(date,pageNum,pageSize);
    }

    @RequestMapping(value = "/visitor",method = RequestMethod.DELETE)
    public Result deleteVisitor(@RequestParam("id") Long id,
                                @RequestParam("uuid") String uuid){

        return visitService.deleteVisitorByIdAndUuid(id,uuid);
    }

    @RequestMapping(value = "/visitLog",method = RequestMethod.DELETE)
    public Result deleteVisitLog(@RequestParam("id") Long id){

        return visitService.deleteVisitLogById(id);
    }


}
