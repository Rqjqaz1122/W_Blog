package com.wfit.mapper;

import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.dto.SeriesDto;
import com.wfit.domain.dto.TagDto;
import com.wfit.domain.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface TagMapper {

    List<Tag> tagList();

    /**
     * 查询每个标签下有多少个文章
     * @return
     */
    List<SeriesDto> selectTag();

    /**
     * 插入标签对应的文章id
     * @param tagList
     * @param id
     */
    void saveTagsByBlog(@Param("tag") List<Tag> tagList, @Param("id") Long id);

    /**
     * 保存标签
     * @param tagList
     */
    void saveTag(@Param("tags") List<Tag> tagList);

    /**
     * 根据文章id删除对应的标签id
     * @param id
     */
    void deleteTagByBlogId(Long id);

    /**
     * 查询所有标签
     * @return
     */
    List<TagDto> selectAllTagsByBlogs();


    /**
     * 根据标签名称查询
     * @param tagName
     * @return
     */
    List<BlogDto> selectTagNameByBlogs(String tagName);

    /**
     * 根据文章id查询标签
     * @param collect
     * @return
     */
    List<TagDto> selectTagById(@Param("ids") List<Long> collect);

    /**
     * 查询标签对应的文章的所有个数
     * @return
     */
    Integer selectTagNameByBlogsCount(String name);

    /**
     * 新增标签
     * @param tag
     */
    void save(Tag tag);

    /**
     * 修改标签
     * @param tag
     */
    void updateTag(Tag tag);

    /**
     * 查询标签下是否有文章
     * @param id
     * @return
     */
    Integer selectTagByIdCount(Long id);

    /**
     * 根据标签id删除
     * @param id
     */
    void deleteByTagId(Long id);
}
