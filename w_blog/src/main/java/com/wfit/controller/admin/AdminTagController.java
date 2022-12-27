package com.wfit.controller.admin;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.domain.Result;
import com.wfit.domain.entity.Tag;
import com.wfit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @RequestMapping("/tags")
    public Result tags(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return tagService.selectTagsByPageNumAndPageSize(pageNum,pageSize);
    }

    @OperationLogAnnotation("添加标签")
    @RequestMapping(value = "tag",method = RequestMethod.POST)
    public Result saveTag(@RequestBody Tag tag){

        return tagService.saveTag(tag);
    }

    @OperationLogAnnotation("修改标签")
    @RequestMapping(value = "tag",method = RequestMethod.PUT)
    public Result updateTag(@RequestBody Tag tag){

        return tagService.updateTag(tag);
    }

    @OperationLogAnnotation("删除标签")
    @RequestMapping(value = "tag",method = RequestMethod.DELETE)
    public Result deleteTag(@RequestParam("id") Long id){

        return tagService.deleteTagById(id);
    }


}
