package com.wfit.domain.vo;

import com.wfit.domain.dto.RandomBlog;
import com.wfit.domain.dto.Site;
import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteVo {

    private List<Site> badges;

    private Map<String,Object> siteInfo;

    private Map<String,Object> introduction;

    private List<NewBlogVo> newBlogList;

    private List<Category> categoryList;

    private List<Tag> tagList;

    private List<RandomBlog> randomBlogList;


}
