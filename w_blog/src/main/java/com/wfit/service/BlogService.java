package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.dto.RandomBlog;
import com.wfit.domain.vo.NewBlogVo;
import com.wfit.domain.vo.PageVo;
import com.wfit.domain.vo.SaveBlogVo;
import com.wfit.domain.vo.VisibilityVo;

import java.util.List;
import java.util.Map;

public interface BlogService {
    /**
     * 获取最新博客
     * @return
     */
    List<NewBlogVo> newBlogList();

    /**
     * 最新的五个文章
     * @return
     */
    List<RandomBlog> randomBlogList();

    /**
     * 分页查询文章信息
     * @param pageNum
     * @return
     */
    PageVo<BlogDto> getBlogsInfoList(Integer pageNum);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    Result findBlogById(Long id);

    /**
     * 查询归档
     * @return
     */
    Result findArchives();

    /**
     * 写博客
     * @param saveBlogVo
     * @return
     */
    Result saveBlog(SaveBlogVo saveBlogVo);

    /**
     * 查询文章信息
     * @param title
     * @param categoryId
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result adminBlogs(String title, Long categoryId, Integer pageNum, Integer pageSize);

    /**
     * 置顶文章
     * @param id
     * @param top
     * @return
     */
    Result blogTop(Long id, Boolean top);

    /**
     * 推荐文章
     * @param id
     * @param recommend
     * @return
     */
    Result blogCommend(Long id, Boolean recommend);

    /**
     * 文章可见性
     * @param id
     * @param visibilityVo
     * @return
     */
    Result blogVisibility(Long id, VisibilityVo visibilityVo);

    /**
     * 修改文章
     * @param saveBlogVo
     * @return
     */
    Result updateBlog(SaveBlogVo saveBlogVo);

    /**
     * 根据id删除文章
     * @param id
     * @return
     */
    Result deleteBlogById(Long id);

    /**
     * 查询所有文章id
     * @return
     */
    List<Long> selectBlogsId();

    /**
     * 修改访问量
     * @param ids
     * @param visits
     */
    void updateBlogViews(List<Long> ids,List<Long> visits);

    /**
     * 后台，根据id查询文章
     * @param id
     * @return
     */
    Result getAdminBlogsById(Long id);

}
