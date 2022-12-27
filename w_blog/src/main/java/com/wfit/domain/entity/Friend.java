package com.wfit.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Friend {

    private Long id;

    private String nickname;

    private String description;

    private String website;

    private String avatar;

    private Boolean isPublished;

    private Boolean published;

    private Integer views;

    private LocalDateTime createTime;

}
