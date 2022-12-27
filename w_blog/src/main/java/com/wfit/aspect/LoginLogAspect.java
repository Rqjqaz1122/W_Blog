package com.wfit.aspect;

import cn.hutool.json.JSONUtil;
import com.wfit.domain.Result;
import com.wfit.domain.entity.LoginLog;
import com.wfit.domain.vo.LoginVo;
import com.wfit.mapper.LoginLogMapper;
import com.wfit.service.LoginLogService;
import com.wfit.utils.IpAddressUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 记录登录日志状态
 */
@Component
@Aspect
public class LoginLogAspect {


    @Autowired
    private LoginLogService loginLogService;

    private static final ExecutorService LOGIN_LOG = Executors.newSingleThreadExecutor();

    @Pointcut("@annotation(com.wfit.annotations.LoginLog)")
    public void pc2(){}

    /**
     * 记录登录日志
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pc2()")
    public Object loginLog(ProceedingJoinPoint joinPoint) throws IOException {
        Object proceed = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String userAgent = request.getHeader("User-Agent");
        HttpServletResponse response = servletRequestAttributes.getResponse();

        String ip = IpAddressUtils.getIpAddress(request);
        String cityInfo = IpAddressUtils.getCityInfo(ip);

        Object[] args = joinPoint.getArgs();
        LoginVo loginVo = (LoginVo)args[0];

        LoginLog loginLog = new LoginLog();
        loginLog.setIp(ip);
        loginLog.setIpSource(cityInfo);
        String username = loginVo.getUsername();
        loginLog.setUsername(username);
        loginLog.setCreateTime(LocalDateTime.now());
        loginLog.setUserAgent(userAgent);
        try {
            proceed = joinPoint.proceed();
            loginLog.setStatus(true);
            loginLog.setDescription("登录成功");
            loginLogService.saveLoginLog(loginLog);
        } catch (Throwable throwable) {
            String message = throwable.getMessage();
            loginLog.setStatus(false);
            loginLog.setDescription(message);
            loginLogService.saveLoginLog(loginLog);

            assert response != null;
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println(JSONUtil.toJsonStr(Result.create(401,"用户或密码错误")));
        }

        return proceed;
    }

}
