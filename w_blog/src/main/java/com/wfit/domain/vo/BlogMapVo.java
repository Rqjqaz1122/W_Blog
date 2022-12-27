package com.wfit.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class BlogMapVo<T> {

    private Map<String, List<T>> blogMap;

    private Integer count;

}
