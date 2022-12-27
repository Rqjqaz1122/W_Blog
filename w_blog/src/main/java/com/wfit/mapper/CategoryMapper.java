package com.wfit.mapper;

import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.dto.SeriesDto;
import com.wfit.domain.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {
    /**
     * 查询分类
     * @return
     */
    List<Category> categoryList();

    /**
     * 根据分类查询所有文章
     * @param categoryName
     * @param pageNum
     * @return
     */
    List<BlogDto> selectBlogsCategoryName(@Param("name") String categoryName, @Param("pageNum") Integer pageNum);

    /**
     * 查询分类下面的博客数量
     * @return
     * @param categoryName
     */
    Long countByCategoryName(@Param("name") String categoryName);

    /**
     * 查询所有文章分类，和文章数量
     * @return
     */
    List<SeriesDto> selectCategoryList();

    /**
     * 保存分类
     * @param category
     */
    void saveCategoryBlog(Category category);

    /**
     * 查询文章下的id
     * @param list
     * @return
     */
    List<Category> selectCategoryById(@Param("ids") List<Long> list);

    /**
     * 根据id删除
     * @param id
     */
    void deleteCategoryById(Long id);

    /**
     * 修改标签
     * @param category
     */
    void updateCategory(Category category);
}
