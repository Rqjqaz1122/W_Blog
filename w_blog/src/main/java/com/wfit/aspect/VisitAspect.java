package com.wfit.aspect;

import com.wfit.domain.dto.UserAgentDTO;
import com.wfit.domain.entity.VisitLog;
import com.wfit.domain.entity.Visitor;
import com.wfit.mapper.VisitLogMapper;
import com.wfit.service.VisitService;
import com.wfit.utils.IpAddressUtils;
import com.wfit.utils.RedisCacheUtil;
import com.wfit.utils.RequestUtils;
import com.wfit.utils.UserAgentUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 访问文章统计
 */
@Component
@Aspect
public class VisitAspect {

    @Autowired
    private RedisCacheUtil cacheUtil;

    @Autowired
    private VisitService visitService;

    @Autowired
    private UserAgentUtils userAgentUtils;




    @Pointcut("@annotation(com.wfit.annotations.VisitAnnotation)")
    public void pc(){}

    @Pointcut("@annotation(com.wfit.annotations.VisitIndex)")
    public void pc1(){}

    @Pointcut("@annotation(com.wfit.annotations.VisitorLog)")
    public void pc2(){}

    @Around("pc()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        Long arg = (Long) args[0];

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        assert servletRequestAttributes != null;

        HttpServletRequest request = servletRequestAttributes.getRequest();

        String ipAddr = IpAddressUtils.getIpAddress(request);
        cacheUtil.visitNumber(ipAddr,arg);

        return joinPoint.proceed();
    }

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor();
    /**
     * 用户进入页面记录日志，只记录一次，使用redis记录
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Before("pc1()")
    public void around(JoinPoint joinPoint) throws Throwable {

        // TODO 后期改进
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        //获取访问ip
        String ipAddr = IpAddressUtils.getIpAddress(request);
        //获取城市
        String cityInfo = IpAddressUtils.getCityInfo(ipAddr);

        Visitor visit = new Visitor();

        Boolean exists = cacheUtil.exists("visit:log", ipAddr);

        if (Boolean.TRUE.equals(exists)) {
            return;
        }
        //单个线程池
        EXECUTOR_SERVICE.submit(()->{
            String userAgent = request.getHeader("User-Agent");
            UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(userAgent);
            visit.setIp(ipAddr);
            visit.setUserAgent(userAgent);
            visit.setIpSource(cityInfo);
            visit.setCreateTime(LocalDateTime.now());
            visit.setLastTime(LocalDateTime.now());
            visit.setPv(0);
            visit.setOs(userAgentDTO.getOs());
            visit.setBrowser(userAgentDTO.getBrowser());
            visit.setUuid(UUID.randomUUID().toString());
            visitService.saveVisitorLog(visit);
            // 开启独立线程进行
            cacheUtil.setSet("visit:log",ipAddr);
        });

    }

    @Around("pc2()")
    public Object visitorLog(ProceedingJoinPoint joinPoint) throws Throwable {

        long beginTime = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        HttpServletRequest request = RequestUtils.getRequest();
        String ipAddress = IpAddressUtils.getIpAddress(request);
        String cityInfo = IpAddressUtils.getCityInfo(ipAddress);
        String userAgent = request.getHeader("User-Agent");

        UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(userAgent);

        VisitLog visitLog = new VisitLog();
        visitLog.setUri(request.getRequestURI());
        visitLog.setIp(ipAddress);
        visitLog.setIpSource(cityInfo);
        visitLog.setUserAgent(userAgent);
        visitLog.setOs(userAgentDTO.getOs());
        visitLog.setBrowser(userAgentDTO.getBrowser());
        visitLog.setMethod(request.getMethod());
        visitLog.setUserAgent(userAgent);

        //异步执行
        EXECUTOR_SERVICE.submit(()->{
            visitService.saveUserVisitorBlogLog(visitLog,joinPoint,endTime - beginTime);
        });
        return proceed;

    }



}
