package com.wfit.controller.admin;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.domain.Result;
import com.wfit.domain.entity.Category;
import com.wfit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categories")
    public Result categories(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return categoryService.categories(pageNum,pageSize);

    }

    @OperationLogAnnotation("删除分类")
    @RequestMapping(value = "/category",method = RequestMethod.DELETE)
    public Result deleteCategoryById(@RequestParam("id") Long id){

        return categoryService.deleteCategoryById(id);
    }

    @OperationLogAnnotation("修改分类")
    @RequestMapping(value = "/category",method = RequestMethod.PUT)
    public Result updateCategory(@RequestBody Category category){

        return categoryService.updateCategory(category);
    }

    @OperationLogAnnotation("添加分类")
    @RequestMapping(value = "/category",method = RequestMethod.POST)
    public Result saveCategory(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }


}
