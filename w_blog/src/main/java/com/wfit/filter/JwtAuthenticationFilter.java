package com.wfit.filter;

import cn.hutool.json.JSONUtil;
import com.wfit.config.LoginUser;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.utils.JwtUtils;
import com.wfit.utils.RedisCacheUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final RedisCacheUtil redisCacheUtil;

    public JwtAuthenticationFilter(RedisCacheUtil redisCacheUtil) {
        this.redisCacheUtil = redisCacheUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if(!StringUtils.hasText(token)){
            filterChain.doFilter(request,response);
            return;
        }
        Claims claims = null;
        try {
             claims = JwtUtils.parseJWT(token);
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSONUtil.toJsonStr(Result.create(402,"需要登录后才能进行该操作")));
            return;
        }

        String subject = claims.getSubject();
        LoginUser loginUser = redisCacheUtil.hashGetBean(RedisConstants.LOGIN_USER, subject, LoginUser.class);

        if(loginUser == null){
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(JSONUtil.toJsonStr(Result.create(402,"登录状态过期，请重新登录")));
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser,null,loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
