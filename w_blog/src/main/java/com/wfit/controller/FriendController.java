package com.wfit.controller;

import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping("/friends")
    @VisitorLog(value = "访问页面",content = "友链")
    public Result friends(){

        return friendService.friends();

    }

}
