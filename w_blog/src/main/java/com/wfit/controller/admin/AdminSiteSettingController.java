package com.wfit.controller.admin;

import com.wfit.domain.Result;
import com.wfit.domain.entity.SiteSetting;
import com.wfit.domain.vo.SiteSettingVo;
import com.wfit.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminSiteSettingController {


    @Autowired
    private SiteService siteService;

    @RequestMapping(value = "/siteSettings",method = RequestMethod.GET)
    public Result siteSettings(){

        return siteService.siteSettings();
    }

    @RequestMapping(value = "/siteSettings",method = RequestMethod.POST)
    public Result updateSiteSettings(@RequestBody SiteSettingVo siteSettingVo){

//        return siteService.updateSiteSettings(siteSettingVo);
        return Result.error("功能未完成");
    }







}
