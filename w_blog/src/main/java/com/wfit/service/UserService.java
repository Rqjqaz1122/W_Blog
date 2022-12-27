package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.vo.LoginVo;

public interface UserService {

    /**
     * 后台登录
     * @param loginVo
     * @return
     */
    Result login(LoginVo loginVo);

}
