package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.vo.SiteSettingVo;

public interface SiteService {

    /**
     * 获取首页底栏信息
     * @return
     */
    Result getSite();


    /**
     * 获取后端信息
     * @return
     */
    Result siteSettings();

    /**
     * 修改siteSetting
     * @param siteSettingVo
     * @return
     */
    Result updateSiteSettings(SiteSettingVo siteSettingVo);


}
