package com.wfit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.domain.Result;
import com.wfit.domain.dto.FriendDto;
import com.wfit.domain.entity.Friend;
import com.wfit.domain.vo.FriendInfo;
import com.wfit.domain.vo.FriendInfoVo;
import com.wfit.domain.vo.FriendVo;
import com.wfit.mapper.FriendMapper;
import com.wfit.mapper.SiteMapper;
import com.wfit.service.FriendService;
import com.wfit.utils.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    private FriendMapper friendMapper;

    @Autowired
    private SiteMapper siteMapper;

    @Override
    public Result friends() {

        FriendInfo friendInfo = siteMapper.selectFriendInfo();
        friendInfo.setCommentEnabled(false);
        friendInfo.setContent(MarkdownUtils.markdownToHtmlExtensions(friendInfo.getContent()));

        List<FriendDto> friendDtos = friendMapper.selectFriendList();

        Map<String,Object> map = new HashMap<>();

        map.put("friendInfo",friendInfo);
        map.put("friendList",friendDtos);

        return Result.ok("获取成功",map);
    }

    @Override
    public Result friendInfo() {

        FriendInfo friendInfo = siteMapper.selectFriendInfo();


        return Result.ok("请求成功",friendInfo);
    }


    @Override
    public Result friends(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Friend> friends = friendMapper.selectFriend();

        friends.forEach(o -> {
            o.setPublished(o.getIsPublished());
        });

        PageInfo<Friend> pageInfo = new PageInfo<Friend>(friends);

        return Result.ok("操作成功", pageInfo);
    }

    @Override
    public Result friendPublished(Long id, Boolean published) {

        friendMapper.friendPublished(id,published);

        return Result.ok("修改成功");
    }

    @Override
    public Result saveFriend(FriendVo friendVo) {

        friendVo.setViews(0L);
        friendVo.setCreateTime(LocalDateTime.now());
        friendMapper.saveFriend(friendVo);

        return Result.ok("新增成功");
    }

    @Override
    public Result updateFriend(FriendVo friendVo) {

        friendMapper.updateFriend(friendVo);

        return Result.ok("修改成功");
    }

    @Override
    public Result deleteFriendById(Long id) {

        friendMapper.deleteFriend(id);

        return Result.ok("删除成功");
    }

    @Override
    public Result updateFriendInfo(FriendInfoVo friendInfoVo) {

        Integer i = friendMapper.updateFriendInfo(friendInfoVo);

        if(i < 1){
            throw new RuntimeException("修改友链页面信息失败");
        }

        return Result.ok("修改成功");
    }

}
