package com.wfit.controller.admin;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.domain.Result;
import com.wfit.domain.entity.Moment;
import com.wfit.service.MomentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminMomentController {

    @Autowired
    private MomentsService momentsService;

    @RequestMapping(value = "/moment",method = RequestMethod.POST)
    @OperationLogAnnotation("发布动态")
    public Result moment(@RequestBody Moment moment){

        return momentsService.moment(moment);
    }

    @RequestMapping(value = "/moments")
    public Result moments(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){

        return momentsService.moments(pageNum,pageSize);
    }

    @OperationLogAnnotation("修改动态可见性")
    @RequestMapping(value = "/moment/published",method = RequestMethod.PUT)
    public Result momentPublished(@RequestParam("id") Long id,@RequestParam("published") Boolean published){

        return momentsService.momentPublished(id,published);
    }

    @RequestMapping("/moment")
    public Result momentGetById(@RequestParam("id") Long id){

        return momentsService.selectMomentById(id);
    }

    @OperationLogAnnotation("修改动态")
    @RequestMapping(value = "/moment",method = RequestMethod.PUT)
    public Result updateMoment(@RequestBody Moment moment){

        return momentsService.updateMoment(moment);
    }

    @OperationLogAnnotation("删除动态")
    @RequestMapping(value = "moment",method = RequestMethod.DELETE)
    public Result deleteMoment(@RequestParam("id") Long id){

        return momentsService.deleteMoment(id);
    }


}
