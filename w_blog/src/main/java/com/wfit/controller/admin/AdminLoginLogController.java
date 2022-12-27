package com.wfit.controller.admin;

import com.wfit.domain.Result;
import com.wfit.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/admin")
public class AdminLoginLogController {

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping("loginLogs")
    public Result loginLogs(@RequestParam(defaultValue = "") String[] date,
                            @RequestParam("pageNum") Integer pageNum,
                            @RequestParam("pageSize") Integer pageSize){


        return loginLogService.loginLogs(date,pageNum,pageSize);
    }

    @RequestMapping(value = "/loginLog",method = RequestMethod.DELETE)
    public Result deleteLoginLog(@RequestParam("id") Long id){

        return loginLogService.deleteLoginLog(id);
    }


}
