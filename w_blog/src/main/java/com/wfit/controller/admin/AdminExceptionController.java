package com.wfit.controller.admin;

import com.wfit.domain.Result;
import com.wfit.mapper.OperationLogMapper;
import com.wfit.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminExceptionController {

    @Autowired
    private OperationLogService operationLogService;

    @RequestMapping("/exceptionLogs")
    public Result exceptionLogs(@RequestParam(defaultValue = "") String [] date,
                                @RequestParam(defaultValue = "1") Integer pageNum,
                                @RequestParam(defaultValue = "10") Integer pageSize){

        return operationLogService.exceptionLogs(date,pageNum,pageSize);
    }

    @RequestMapping(value = "/exceptionLog",method = RequestMethod.DELETE)
    public Result deleteOperationLog(@RequestParam("id") Long id){

        return operationLogService.deleteExceptionLogById(id);
    }


}
