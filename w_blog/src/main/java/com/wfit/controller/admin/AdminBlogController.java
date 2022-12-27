package com.wfit.controller.admin;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.domain.Result;
import com.wfit.domain.vo.SaveBlogVo;
import com.wfit.domain.vo.VisibilityVo;
import com.wfit.mapper.BlogMapper;
import com.wfit.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminBlogController {

    @Autowired
    private BlogService blogService;

    @OperationLogAnnotation("置顶文章")
    @RequestMapping(value = "/blog/top",method = RequestMethod.PUT)
    public Result blogTop(@RequestParam("id") Long id,@RequestParam("top") Boolean top){

        return blogService.blogTop(id,top);
    }
    @OperationLogAnnotation("修改推荐文章")
    @RequestMapping(value = "/blog/recommend",method = RequestMethod.PUT)
    public Result blogCommend(@RequestParam("id") Long id,@RequestParam("recommend") Boolean recommend){

        return blogService.blogCommend(id,recommend);
    }

    @OperationLogAnnotation("修改文章可见性")
    @RequestMapping(value = "blog/{id}/visibility",method = RequestMethod.PUT)
    public Result blogVisibility(@PathVariable("id") Long id,
                                 @RequestBody VisibilityVo visibilityVo){

        return blogService.blogVisibility(id,visibilityVo);
    }

    @OperationLogAnnotation("修改文章")
    @RequestMapping(value = "blog",method = RequestMethod.PUT)
    public Result updateBlog(@RequestBody SaveBlogVo saveBlogVo){

        return blogService.updateBlog(saveBlogVo);
    }

    @OperationLogAnnotation("删除文章")
    @RequestMapping(value = "blog",method = RequestMethod.DELETE)
    public Result deleteBlog(@RequestParam("id") Long id){


        return blogService.deleteBlogById(id);
    }




}
