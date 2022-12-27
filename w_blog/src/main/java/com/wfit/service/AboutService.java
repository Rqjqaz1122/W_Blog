package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.vo.AboutVo;

public interface AboutService {
    /**
     * 关于我
     * @return
     */
    Result aboutMe();

    /**
     * 修改关于我
     * @param about
     * @return
     */
    Result updateAbout(AboutVo about);

    /**
     * 管理端返回关于我信息
     * @return
     */
    Result adminAboutMe();

}
