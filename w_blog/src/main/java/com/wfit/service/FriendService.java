package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.vo.FriendInfoVo;
import com.wfit.domain.vo.FriendVo;

public interface FriendService {

    /**
     * 获取全部友联
     * @return
     */
    Result friends();

    /**
     * 获取友联下说明
     * @return
     */
    Result friendInfo();


    /**
     * 分页查询友联
     * @return
     */
    Result friends(Integer pageNum,Integer pageSize);

    /**
     * 修改是否可见
     * @param id
     * @param published
     * @return
     */
    Result friendPublished(Long id, Boolean published);

    /**
     * 新增友联
     * @param friendVo
     * @return
     */
    Result saveFriend(FriendVo friendVo);

    /**
     * 修改友联
     * @param friendVo
     * @return
     */
    Result updateFriend(FriendVo friendVo);

    /**
     * 根据id删除友联
     * @param id
     * @return
     */
    Result deleteFriendById(Long id);

    /**
     * 修改友链页面信息
     * @param content
     * @return
     */
    Result updateFriendInfo(FriendInfoVo content);

}
