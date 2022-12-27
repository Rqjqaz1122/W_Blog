package com.wfit.aspect;

import cn.hutool.json.JSONUtil;
import com.wfit.domain.dto.UserAgentDTO;
import com.wfit.domain.entity.ExceptionLog;
import com.wfit.mapper.OperationLogMapper;
import com.wfit.utils.IpAddressUtils;
import com.wfit.utils.RequestUtils;
import com.wfit.utils.UserAgentUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class ExceptionAspect {

    @Autowired
    private UserAgentUtils userAgentUtils;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Pointcut("execution(* com.wfit.controller.*.*(..))")
    public void pc(){}

//    @Around("pc()")
    @AfterThrowing(value = "pc()",throwing = "e")
    public void exception(JoinPoint joinPoint,Throwable e) throws Throwable {

        HttpServletRequest request = RequestUtils.getRequest();
        String userAgent = request.getHeader("User-Agent");
        String ip = IpAddressUtils.getIpAddress(request);
        String cityInfo = IpAddressUtils.getCityInfo(ip);
        UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(userAgent);


        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String name = method.getDeclaringClass().getName() + "." + method.getName();
        ExceptionLog exceptionLog = new ExceptionLog();
        exceptionLog.setDescription(name);
        exceptionLog.setMethod(request.getMethod());
        exceptionLog.setError(e.toString());
        exceptionLog.setCreateTime(LocalDateTime.now());
        exceptionLog.setIp(ip);
        exceptionLog.setUserAgent(userAgent);
        exceptionLog.setBrowser(userAgentDTO.getBrowser());
        exceptionLog.setIpSource(cityInfo);
        exceptionLog.setOs(userAgentDTO.getOs());
        exceptionLog.setUri(request.getRequestURI());
        exceptionLog.setParam(JSONUtil.toJsonStr(joinPoint.getArgs()));
        operationLogMapper.saveExceptionLog(exceptionLog);
    }


}
