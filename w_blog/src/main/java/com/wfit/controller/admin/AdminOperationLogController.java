package com.wfit.controller.admin;

import com.wfit.domain.Result;
import com.wfit.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminOperationLogController {

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping("/operationLogs")
    public Result operationLogs(@RequestParam(defaultValue = "") String[] date,
                                @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return operationLogService.operationLogs(date,pageNum,pageSize);
    }

    @RequestMapping(value = "/operationLog",method = RequestMethod.DELETE)
    public Result deleteOperationLog(@RequestParam("id") Long id){

        return operationLogService.deleteOperation(id);
    }



}
