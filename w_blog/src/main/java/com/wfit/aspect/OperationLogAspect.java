package com.wfit.aspect;

import cn.hutool.json.JSONUtil;
import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.config.LoginUser;
import com.wfit.domain.dto.UserAgentDTO;
import com.wfit.domain.entity.OperationLog;
import com.wfit.service.OperationLogService;
import com.wfit.utils.IpAddressUtils;
import com.wfit.utils.UserAgentUtils;
import io.jsonwebtoken.ExpiredJwtException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
@Component
public class OperationLogAspect {


    @Autowired
    private UserAgentUtils userAgentUtils;

    @Autowired
    private OperationLogService operationLogService;

    @Pointcut("@annotation(com.wfit.annotations.OperationLogAnnotation)")
    public void pc(){}

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();

    @Around("pc()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {


        long beginTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        LoginUser loginUser = (LoginUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String ipAddress = IpAddressUtils.getIpAddress(request);
        String cityInfo = IpAddressUtils.getCityInfo(ipAddress);
        try{
            EXECUTOR_SERVICE.submit(() ->{
                //获取日志信息
                String userAgent = request.getHeader("User-Agent");
                MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
                Method method = methodSignature.getMethod();
                OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);

                String username = loginUser.getUser().getUsername();
                String uri = request.getRequestURI();
                String method1 = request.getMethod();
                Object[] args = joinPoint.getArgs();
                String param = JSONUtil.toJsonStr(args);
                UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(userAgent);

                //封装日志信息
                OperationLog operationLog = new OperationLog();
                operationLog.setDescription(annotation.value());
                operationLog.setIp(ipAddress);
                operationLog.setIpSource(cityInfo);
                operationLog.setUserAgent(userAgent);
                operationLog.setUsername(username);
                operationLog.setUri(uri);
                operationLog.setMethod(method1);
                operationLog.setParam(param);
                operationLog.setOs(userAgentDTO.getOs());
                operationLog.setBrowser(userAgentDTO.getBrowser());
                operationLog.setCreateTime(LocalDateTime.now());
                operationLog.setTimes(endTime - beginTime);
                operationLogService.saveOperationLog(operationLog);

            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return proceed;


    }



}
