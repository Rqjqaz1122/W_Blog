package com.wfit.interceptor;

import cn.hutool.json.JSONUtil;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.utils.IpAddressUtils;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class VisitorInterceptor implements HandlerInterceptor {

    private final RedisCacheUtil redisCacheUtil;

    private static final Long VISITOR_COUNT = 50L;

    public VisitorInterceptor(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ipAddress = IpAddressUtils.getIpAddress(request);
        String string = redisCacheUtil.getString(RedisConstants.VISITOR_COUNT + ipAddress);

        if(string == null || "".equals(string)){
            redisCacheUtil.increment(RedisConstants.VISITOR_COUNT + ipAddress,1L);
            redisCacheUtil.expire(RedisConstants.VISITOR_COUNT + ipAddress,3L, TimeUnit.SECONDS);
            return true;
        }

        long l = Long.parseLong(string);

        if(l >= VISITOR_COUNT){
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().println(JSONUtil.toJsonStr(Result.create(400,"请勿恶意请求")));
            return false;
        }

        redisCacheUtil.increment(RedisConstants.VISITOR_COUNT + ipAddress,1L);
        redisCacheUtil.expire(RedisConstants.VISITOR_COUNT + ipAddress,3L, TimeUnit.SECONDS);

        return true;
    }
}
