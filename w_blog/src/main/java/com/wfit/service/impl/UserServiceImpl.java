package com.wfit.service.impl;

import cn.hutool.json.JSONUtil;
import com.wfit.config.LoginUser;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.vo.LoginVo;
import com.wfit.service.UserService;
import com.wfit.utils.JwtUtils;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public Result login(LoginVo loginVo) {

        // TODO 后期改进
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginVo.getUsername(),loginVo.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if(authenticate == null){
            throw new RuntimeException("用户名或密码错误");
        }

        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();

        String jwt = JwtUtils.createJWT(loginUser.getUser().getId().toString());

        Map<String,Object> map = new HashMap<>();

        map.put("token",jwt);
        map.put("user",loginUser.getUser());

        redisCacheUtil.setHash(RedisConstants.LOGIN_USER,loginUser.getUser().getId().toString(), JSONUtil.toJsonStr(loginUser));

        return Result.ok("登录成功",map);
    }
}
