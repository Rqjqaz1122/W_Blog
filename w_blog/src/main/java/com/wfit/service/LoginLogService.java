package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.entity.LoginLog;

public interface LoginLogService {


    /**
     * 记录登录日志
     * @param loginLog
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 查询登录日志
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result loginLogs(String[] date, Integer pageNum, Integer pageSize);

    /**
     * 删除日志
     * @param id
     * @return
     */
    Result deleteLoginLog(Long id);
}
