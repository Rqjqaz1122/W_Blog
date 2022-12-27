package com.wfit.mapper;

import com.wfit.domain.dto.ArchiveDto;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.dto.RandomBlog;
import com.wfit.domain.entity.Blog;
import com.wfit.domain.vo.BlogVo;
import com.wfit.domain.vo.NewBlogVo;
import com.wfit.domain.vo.SaveBlogVo;
import com.wfit.domain.vo.VisibilityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BlogMapper {
    /**
     * 查询最新博客文章
     * @return
     */
    List<NewBlogVo> newBlogList();

    /**
     * 最新的4个文章
     * @return
     */
    List<RandomBlog> randomBlogList();

    /**
     * 分页查询文章列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<BlogDto> getBlogsInfoList(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    BlogVo selectBlogById(Long id);

    /**
     * 查询文章归档
     * @return
     */
    List<ArchiveDto> findArchives();

    /**
     * 查询博客记录数
     * @return
     */
    Integer count();

    /**
     * 写文章
     * @param saveBlogVo
     */
    void saveBlog(SaveBlogVo saveBlogVo);

    /**
     * 获取文章列表
     * @param title
     * @param categoryId
     * @return
     */
    List<Blog> getBlogsByTitleOrCategoryId(@Param("title") String title, @Param("categoryId") Long categoryId);

    /**
     * 置顶blog
     * @param id
     * @param top
     */
    void blogTop(@Param("id") Long id, @Param("top") int top);

    /**
     * 置顶blog
     * @param id
     * @param recommend
     */
    void blogCommend(@Param("id") Long id,@Param("recommend") int recommend);

    /**
     * 文章可见性
     * @param id
     * @param visibilityVo
     */
    void blogVisibility(@Param("id") Long id, @Param("v") VisibilityVo visibilityVo);

    /**
     * 修改文章
     * @param saveBlogVo
     */
    void updateBlog(@Param("s") SaveBlogVo saveBlogVo);

    /**
     * 根据id删除文章
     * @param id
     */
    void deleteBlogById(Long id);

    /**
     * 根据分类id查询文章
     * @param id
     * @return
     */
    Integer selectByCategoryIdCount(Long id);

    /**
     * 查询所有文章id
     * @return
     */
    List<Long> selectBlogsId();


    /**
     * 修改访问量
     * @param id
     * @param visit
     */
    void updateBlogView(@Param("id") Long id,@Param("visit") Long visit);
}
