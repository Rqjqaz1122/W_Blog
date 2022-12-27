package com.wfit.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendVo {

    private Long id;

    private String avatar;

    private String description;

    private String nickname;

    private String website;

    private Boolean isPublished;

    private Boolean published;

    private LocalDateTime createTime;

    private Long views;
}
