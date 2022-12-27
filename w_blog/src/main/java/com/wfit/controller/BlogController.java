package com.wfit.controller;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.annotations.VisitAnnotation;
import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.vo.PageVo;
import com.wfit.domain.vo.SaveBlogVo;
import com.wfit.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/blogs")
    public Result indexBlogs(@RequestParam(defaultValue = "1") Integer pageNum){

        PageVo<BlogDto> list = blogService.getBlogsInfoList(pageNum);

        return Result.ok("请求成功",list);
    }

    @RequestMapping("/blog")
    @VisitAnnotation
    @VisitorLog(value = "查看博客",content = "通过id查询")
    public Result findBlogById(Long id){

        if(id == null || id == 0){
            return Result.error("id错误");
        }

        return blogService.findBlogById(id);
    }

    /**
     * 文章归档
     * @return
     */
    @RequestMapping("/archives")
    public Result findArchives(){

        return blogService.findArchives();
    }

    @RequestMapping("/searchBlog")
    public Result searchBlog(String query){

        return Result.create(500,"功能暂时未完成");
    }

    @OperationLogAnnotation("发布文章")
    @RequestMapping(value = "/admin/blog",method = RequestMethod.POST)
    public Result saveBlog(@RequestBody SaveBlogVo saveBlogVo){

        return blogService.saveBlog(saveBlogVo);
    }

    @RequestMapping("/admin/blogs")
    public Result adminBlogs(@RequestParam(value = "title",defaultValue = "")String title,
                             @RequestParam(value = "categoryId",defaultValue = "") Long categoryId,
                             @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                             @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return blogService.adminBlogs(title,categoryId,pageNum,pageSize);
    }

    @RequestMapping(value = "/admin/blog",method = RequestMethod.GET)
    public Result getBlogById(Long id){

        if(id == null){
            return Result.error("错误请求");
        }

        return blogService.getAdminBlogsById(id);
    }



}
