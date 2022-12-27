package com.wfit.mapper;

import com.wfit.domain.entity.SiteSetting;
import com.wfit.domain.vo.FriendInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SiteMapper {

    /**
     * 查询底部栏信息
     * @return
     */
    List<SiteSetting> selectSite();


    /**
     * 查询友链的底部提示
     * @return
     */
    FriendInfo selectFriendInfo();

    /**
     * 批量删除settings
     * @param deleteIds
     * @return
     */
    Integer deleteSiteSettingsByIds(@Param("ids") List<Long> deleteIds);

    /**
     * 修改setting
     * @param settings
     * @return
     */
    Integer updateSiteSettings(@Param("settings") List<SiteSetting> settings);

}
