package com.wfit.controller;

import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/category")
    @VisitorLog(value = "访问页面",content = "归档")
    public Result categoryName(
            @RequestParam("categoryName") String categoryName,
            @RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum){

        if(categoryName == null || "".equals(categoryName)){
            return Result.error("分类属性不能为空");
        }

        return categoryService.findBlogsByCategoryName(categoryName,pageNum);
    }

    @RequestMapping("/admin/categoryAndTag")
    public Result categoryAndTag(){

        return categoryService.categoryAndTag();
    }


}
