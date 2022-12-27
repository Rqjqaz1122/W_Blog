package com.wfit.controller;

import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.service.MomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MomentsController {

    @Autowired
    private MomentsService momentsService;

    @RequestMapping("/moments")
    @VisitorLog(value = "访问页面",content = "动态")
    public Result moments(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum){

        if(pageNum == null){
            return Result.error("错误操作");
        }

        return momentsService.selectMomentsByPageNum(pageNum);
    }

    @RequestMapping(value = "/moment/like/{id}",method = RequestMethod.POST)
    @VisitorLog(value = "点赞",content = "动态")
    public Result like(@PathVariable("id") Long id, HttpServletRequest request){

        if(id == null){
            return Result.error("错误请求");
        }

        return momentsService.like(id,request);
    }


}
