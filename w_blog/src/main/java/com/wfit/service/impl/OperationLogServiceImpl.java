package com.wfit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.domain.Result;
import com.wfit.domain.entity.ExceptionLog;
import com.wfit.domain.entity.OperationLog;
import com.wfit.mapper.OperationLogMapper;
import com.wfit.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    private OperationLogMapper operationLogMapper;
    @Override
    public void saveOperationLog(OperationLog operationLog) {

        operationLogMapper.saveOperationLog(operationLog);

    }

    @Override
    public Result operationLogs(String[] date, Integer pageNum, Integer pageSize) {

        String beginTime = null;
        String endTime = null;

        if(date.length == 2){
             beginTime = date[0];
             endTime = date[1];
        }


        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<OperationLog> operationLog = operationLogMapper.selectOperationLogs(beginTime,endTime);
        PageInfo<OperationLog> pageInfo = new PageInfo<>(operationLog);

        return Result.ok("获取成功",pageInfo);
    }

    @Override
    public Result deleteOperation(Long id) {

        if(id == null){
            return Result.error("错误请求");
        }

        operationLogMapper.deleteOperationById(id);

        return Result.ok("删除成功");
    }

    @Override
    public Result exceptionLogs(String[] date, Integer pageNum, Integer pageSize) {
        String beginTime = null;
        String endTime = null;
        if(date.length == 2){
             beginTime = date[0];
             endTime = date[1];
        }

        String orderBy = "create_time desc";

        PageHelper.startPage(pageNum,pageSize,orderBy);

        List<ExceptionLog> exceptionLogs = operationLogMapper.selectExceptionLogs(beginTime,endTime);
        PageInfo<ExceptionLog> pageInfo = new PageInfo<ExceptionLog>(exceptionLogs);

        return Result.ok("操作成功",pageInfo);
    }

    @Override
    public Result deleteExceptionLogById(Long id) {

        operationLogMapper.deleteExceptionLogById(id);

        return Result.ok("操作成功");
    }
}
