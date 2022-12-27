package com.wfit.controller.admin;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.domain.Result;
import com.wfit.domain.entity.About;
import com.wfit.domain.vo.AboutVo;
import com.wfit.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminAboutController {

    @Autowired
    private AboutService aboutService;

    @RequestMapping("/about")
    public Result about(){

        return aboutService.adminAboutMe();
    }

    @OperationLogAnnotation("修改关于我")
    @RequestMapping(value = "/about",method = RequestMethod.PUT)
    public Result updateAbout(@RequestBody AboutVo aboutVo){

        return aboutService.updateAbout(aboutVo);
    }


}
