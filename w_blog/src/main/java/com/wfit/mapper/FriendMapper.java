package com.wfit.mapper;

import com.wfit.domain.dto.FriendDto;
import com.wfit.domain.entity.Friend;
import com.wfit.domain.vo.FriendInfoVo;
import com.wfit.domain.vo.FriendVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FriendMapper {
    /**
     * 查询友链
     * @return
     */
    List<FriendDto> selectFriendList();

    List<Friend> selectFriend();

    /**
     * 修改友联
     * @param id
     * @param published
     */
    void friendPublished(@Param("id") Long id, @Param("p") Boolean published);

    /**
     * 新增友联
     * @param friendVo
     */
    void saveFriend(FriendVo friendVo);

    /**
     * 修改友联
     * @param friendVo
     */
    void updateFriend(FriendVo friendVo);

    /**
     * 根据id删除友联
     * @param id
     */
    void deleteFriend(Long id);

    /**
     * 修改友链页面信息
     * @param friendInfoVo
     * @return
     */
    Integer updateFriendInfo(FriendInfoVo friendInfoVo);

}
