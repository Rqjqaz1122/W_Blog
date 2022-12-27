package com.wfit.controller;

import com.wfit.annotations.VisitIndex;
import com.wfit.domain.Result;
import com.wfit.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SiteController {

    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "/site",method = RequestMethod.GET)
    @VisitIndex
    public Result site(){
        return siteService.getSite();
    }



}
