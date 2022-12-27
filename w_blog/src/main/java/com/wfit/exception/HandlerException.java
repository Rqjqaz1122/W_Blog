package com.wfit.exception;

import com.wfit.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class HandlerException {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Result runTimeEx(HttpServletRequest request,RuntimeException e){
        log.error("Request URL :{},Exception:",request,e);

        return Result.error("服务器异常");
    }



}
