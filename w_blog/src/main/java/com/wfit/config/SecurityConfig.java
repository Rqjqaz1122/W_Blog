package com.wfit.config;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.wfit.domain.Result;
import com.wfit.filter.JwtAuthenticationFilter;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 加密方式
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //允许跨域
        http.cors();
        //关闭csrf
        http.csrf().disable();
        //关闭session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //关闭提供的退出方式
        http.logout().disable();

        http.authorizeRequests()
                .antMatchers("/admin/login").anonymous()
                .antMatchers("/admin/**").authenticated();

        http.addFilterBefore(new JwtAuthenticationFilter(redisCacheUtil), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
