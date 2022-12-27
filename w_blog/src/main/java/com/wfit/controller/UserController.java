package com.wfit.controller;

import com.wfit.annotations.LoginLog;
import com.wfit.domain.Result;
import com.wfit.domain.vo.LoginVo;
import com.wfit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @LoginLog
    public Result login(@RequestBody LoginVo loginVo){

        if(loginVo.getUsername() == null || "".equals(loginVo.getUsername())){
            return Result.error("用户名不能为空");
        }

        if(loginVo.getPassword() == null || "".equals(loginVo.getPassword())){
            return Result.error("密码不能为空");
        }

        return userService.login(loginVo);
    }




}
