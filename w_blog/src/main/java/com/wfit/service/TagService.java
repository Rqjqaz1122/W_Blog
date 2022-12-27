package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.entity.Tag;

import java.util.List;
import java.util.Map;

public interface TagService {
    /**
     * 获取标签
     * @return
     */
    List<Tag> tagList();

    /**
     * 保存标签
     * @param tagList
     * @param id
     */
    void saveTagsByBlog(List<Object> tagList, Long id);

    /**
     * 根据文章id查询标签
     * @return
     * @param blogDtos
     */
    Map<Long, List<Tag>> selectTagsByBlogsId(List<BlogDto> blogDtos);

    /**
     * 通过标签名查询标签下的文章
     * @param tagName
     * @return
     */
    Result getTagNameByBlogs(String tagName);

    /**
     * 分页查询标签
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result selectTagsByPageNumAndPageSize(Integer pageNum, Integer pageSize);

    /**
     * 新增标签
     * @param tag
     * @return
     */
    Result saveTag(Tag tag);

    /**
     * 修改标签
     * @param tag
     * @return
     */
    Result updateTag(Tag tag);

    /**
     * 删除标签
     * @param id
     * @return
     */
    Result deleteTagById(Long id);

}
