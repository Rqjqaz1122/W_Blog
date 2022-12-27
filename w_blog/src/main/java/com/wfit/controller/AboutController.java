package com.wfit.controller;

import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {

    @Autowired
    private AboutService aboutService;

    @RequestMapping("/about")
    @VisitorLog(value = "访问页面",content = "关于我")
    public Result aboutMe(){
        return aboutService.aboutMe();
    }


}
