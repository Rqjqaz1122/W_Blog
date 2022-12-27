package com.wfit.config;

import com.wfit.constants.RedisConstants;
import com.wfit.interceptor.VisitorInterceptor;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .maxAge(3600);
    }

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new VisitorInterceptor(redisCacheUtil))
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/**");
    }
}



