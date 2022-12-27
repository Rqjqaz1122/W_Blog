package com.wfit.controller;

import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/tag")
    @VisitorLog(value = "访问页面",content = "标签")
    public Result getTagNameByBlogs(String tagName){

        if(!StringUtils.hasText(tagName)){
            return Result.error("标签名不能为空");
        }
        return tagService.getTagNameByBlogs(tagName);
    }


}
