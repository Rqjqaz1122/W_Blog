package com.wfit.domain.vo;

import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class BlogVo {

    private Long id;

    private String title;

    private String content;

    private String firstPicture;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

//    private Boolean commentEnabled;

    private String description;

    private Category category;

    private Boolean privacy;

    private Boolean top;

    private Long views;

    private Long words;

    private Long readTime;

    private List<Tag> tags;

}
