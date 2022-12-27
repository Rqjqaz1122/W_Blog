package com.wfit.service.impl;

import com.wfit.config.LoginUser;
import com.wfit.domain.entity.User;
import com.wfit.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //查询数据库中是否有该用户
        User user = userMapper.selectByUsername(username);

        //TODO 后期改进自定义异常
        if(user == null){
            throw new UsernameNotFoundException("没有该用户");
        }

        return new LoginUser(user);
    }
}
