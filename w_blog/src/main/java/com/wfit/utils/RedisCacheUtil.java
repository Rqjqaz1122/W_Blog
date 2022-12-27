package com.wfit.utils;

import cn.hutool.json.JSONUtil;
import com.wfit.constants.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import sun.util.resources.bg.LocaleNames_bg;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisCacheUtil {

    private final StringRedisTemplate redisTemplate;

    public RedisCacheUtil(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setString(String key, Object value){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, JSONUtil.toJsonStr(value));
    }

    public void setString(String key, Object value, Long ttl,TimeUnit timeUnit){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, JSONUtil.toJsonStr(value),ttl,timeUnit);
    }

    public String getString(String key){

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        return ops.get(key);
    }

    public <T> T stringGetBean(String key,Class<T> clazz){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String json = ops.get(key);
        return JSONUtil.toBean(json, clazz);
    }

    public <T> List<T> stringGetList(String key,Class<T> clazz){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        String jsonList = ops.get(key);
        return JSONUtil.toList(jsonList,clazz);
    }

    public void visitNumber(String ip, Long id){

        String value = "user_" + ip;

        HyperLogLogOperations<String, String> ops = redisTemplate.opsForHyperLogLog();

        ops.add(RedisConstants.BLOG_VISITS + id,value);
    }

    public Long getVisit(Long id){

        HyperLogLogOperations<String, String> ops = redisTemplate.opsForHyperLogLog();
        return ops.size(RedisConstants.BLOG_VISITS + id);
    }


    public void setHash(String key,String field,Object value){
        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        ops.put(key,field,JSONUtil.toJsonStr(value));
    }

    public <T> List<T> hashGetList(String key,String field,Class<T> clazz){

        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        String json = (String)ops.get(key, field);

        return JSONUtil.toList(json,clazz);
    }

    public <T> T hashGetBean(String key,String field,Class<T> clazz){

        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();
        String json = (String)ops.get(key, field);

        if(!StringUtils.hasText(json)){
            return null;
        }

        return JSONUtil.toBean(json,clazz);
    }

    public void setSet(String key,String value){

        SetOperations<String, String> ops = redisTemplate.opsForSet();

        ops.add(key,value);
    }

    public Boolean exists(String key,String field){
        SetOperations<String, String> ops = redisTemplate.opsForSet();

        return ops.isMember(key, field);
    }

    public Boolean deleteCache(String key){

        return redisTemplate.delete(key);
    }

    public void expire(String key,Long ttl,TimeUnit timeUnit){
        redisTemplate.expire(key,ttl,timeUnit);
    }

    public void deleteHash(String key,Object field){

        HashOperations<String, Object, Object> ops = redisTemplate.opsForHash();

        ops.delete(key,field);

    }

    public void increment(String key,Long number){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.increment(key, number);
    }

    public Long getNumber(String key){
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        return ops.size(key);
    }



}
