package com.wfit.mapper;

import com.wfit.domain.entity.ExceptionLog;
import com.wfit.domain.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OperationLogMapper {

    /**
     * 新增操作日志
     * @param operationLog
     */
    void saveOperationLog(OperationLog operationLog);

    /**
     * 查询操作日志
     * @param beginTime
     * @param endTime
     * @return
     */
    List<OperationLog> selectOperationLogs(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 根据id删除
     * @param id
     */
    void deleteOperationById(Long id);

    /**
     * 记录异常信息
     * @param exceptionLog
     */
    void saveExceptionLog(ExceptionLog exceptionLog);

    /**
     * 查询异常日志
     * @param beginTime
     * @param endTime
     * @return
     */
    List<ExceptionLog> selectExceptionLogs(String beginTime, String endTime);

    /**
     * 根据id删除异常日志
     * @param id
     */
    void deleteExceptionLogById(Long id);
}
