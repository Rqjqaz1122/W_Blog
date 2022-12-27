package com.wfit.domain.entity;

import java.time.LocalDateTime;
import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Moment)表实体类
 *
 * @author makejava
 * @since 2022-11-25 19:12:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Moment  {

    private Long id;
    //动态内容
    private String content;
    //创建时间
    private LocalDateTime createTime;
    //点赞数量
    private Integer likes;
    //是否公开
    private Boolean isPublished;

    private Boolean published;


}
