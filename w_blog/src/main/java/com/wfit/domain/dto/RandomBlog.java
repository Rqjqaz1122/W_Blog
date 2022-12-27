package com.wfit.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RandomBlog {

    private Long id;

    private String title;

    private String firstPicture;

    private LocalDateTime createTime;

    private Boolean privacy;

}
