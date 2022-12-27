package com.wfit.controller.admin;

import com.wfit.annotations.OperationLogAnnotation;
import com.wfit.domain.Result;
import com.wfit.domain.entity.Friend;
import com.wfit.domain.vo.FriendInfoVo;
import com.wfit.domain.vo.FriendVo;
import com.wfit.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminFriendController {

    @Autowired
    private FriendService friendService;

    @RequestMapping("/friendInfo")
    public Result friendInfo(){

        return friendService.friendInfo();
    }

    @RequestMapping("/friends")
    public Result friends(@RequestParam(value = "pageNum" ,defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize){


        return friendService.friends(pageNum,pageSize);
    }

    @OperationLogAnnotation("修改友联可见性")
    @RequestMapping(value = "/friend/published",method = RequestMethod.PUT)
    public Result friendPublished(@RequestParam("id") Long id,
                                  @RequestParam("published") Boolean published){

        return friendService.friendPublished(id,published);

    }
    @OperationLogAnnotation("添加友联")
    @RequestMapping(value = "/friend",method = RequestMethod.POST)
    public Result saveFriend(@RequestBody FriendVo friendVo){

        return friendService.saveFriend(friendVo);
    }
    @OperationLogAnnotation("修改友联")
    @RequestMapping(value = "/friend",method = RequestMethod.PUT)
    public Result updateFriend(@RequestBody FriendVo friendVo){

        return friendService.updateFriend(friendVo);
    }

    @OperationLogAnnotation("删除友联")
    @RequestMapping(value = "/friend",method = RequestMethod.DELETE)
    public Result deleteFriend(@RequestParam("id") Long id){

        return friendService.deleteFriendById(id);
    }

    @RequestMapping(value = "/friendInfo/content",method = RequestMethod.PUT)
    public Result updateFriendInfo(@RequestBody FriendInfoVo friendInfoVo){

        return friendService.updateFriendInfo(friendInfoVo);
    }




}
