package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.entity.OperationLog;

public interface OperationLogService {

    /**
     * 添加操作日志
     * @param operationLog
     */
    void saveOperationLog(OperationLog operationLog);

    /**
     * 分页查询操作日志
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result operationLogs(String[] date, Integer pageNum, Integer pageSize);

    /**
     * 根据id删除操作日志
     * @param id
     * @return
     */
    Result deleteOperation(Long id);

    /**
     * 分页查询异常信息
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result exceptionLogs(String[] date, Integer pageNum, Integer pageSize);

    /**
     * 根据id删除异常日志
     * @param id
     * @return
     */
    Result deleteExceptionLogById(Long id);
}
