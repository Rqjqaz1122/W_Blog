package com.wfit.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class SaveBlogVo {

    private Long id;

    private String title;

    private String content;

    private String firstPicture;

    private String words;

    private String description;

    private Boolean published;

    private Long readTime;

    private Boolean recommend;

    private List<Object> tagList;

    private Boolean top;

    private int views;

    private Boolean appreciation;

    private Object cate;

    private Boolean commentEnabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long userId;




}
