package com.wfit.controller;

import com.wfit.domain.Result;
import com.wfit.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class DashboardController {


    @Autowired
    private DashboardService dashboardService;

    @RequestMapping("/dashboard")
    public Result dashboard(){


        return dashboardService.dashboard();
    }


}
