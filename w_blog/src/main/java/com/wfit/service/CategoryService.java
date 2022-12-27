package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    /**
     * 获取菜单集合
     * @return
     */
    List<Category> categoryList();

    /**
     * 根据分类查询所有文章
     * @param categoryName
     * @param pageNum
     * @return
     */
    Result findBlogsByCategoryName(String categoryName, Integer pageNum);

    /**
     * 查询标签
     * @return
     */
    Result categoryAndTag();

    /**
     * 查询菜单
     * @param list
     * @return
     */
    Map<Long, Category> selectCategoryById(List<BlogDto> list);

    /**
     * 查询分类
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result categories(Integer pageNum, Integer pageSize);

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    Result deleteCategoryById(Long id);

    /**
     * 修改标签
     * @param category
     * @return
     */
    Result updateCategory(Category category);

    /**
     * 新增标签
     * @param category
     * @return
     */
    Result saveCategory(Category category);

}
