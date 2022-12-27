package com.wfit.domain.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BlogDto {

    private Long id;

    private String title;

    private LocalDateTime createTime;

    private String description;

    private Category category;

    private Boolean privacy;

    private Boolean top;

    private Long views;

    private Long words;

    private Long readTime;

    private List<Tag> tags;

}
