package com.wfit.domain.entity;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Blog)表实体类
 *
 * @author makejava
 * @since 2022-11-27 15:43:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog  {

    private Long id;

    //文章标题
    private String title;
    //文章首图，用于随机文章展示
    private String firstPicture;
    //文章正文
    private String content;
    //描述
    private String description;
    //公开或私密
    private Boolean published;
    //推荐开关
    private Boolean recommend;
    //赞赏开关
    private Boolean appreciation;
    //评论开关
    private Boolean commentEnabled;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    //浏览次数
    private Integer views;
    //文章字数
    private Integer words;
    //阅读时长(分钟)
    private Integer readTime;
    //文章分类
    private Long categoryId;
    //是否置顶
    private Boolean top;
    //密码保护
    private String password;
    //文章作者
    private Long userId;

    private Category category;


}
