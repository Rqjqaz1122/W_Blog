package com.wfit.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CopyBeanUtil {

    public static <T> T copyBean(Object o,Class<T> clazz){
        T t = null;
        try {
            t = clazz.newInstance();
            BeanUtils.copyProperties(o,t);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

    public static <T> List<T> copyList(List<?> list,Class<T> clazz){

        return list.stream()
                .map(o -> copyBean(o,clazz))
                .collect(Collectors.toList());
    }



}
