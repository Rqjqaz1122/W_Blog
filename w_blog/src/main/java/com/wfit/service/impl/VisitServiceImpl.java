package com.wfit.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.annotations.VisitorLog;
import com.wfit.domain.Result;
import com.wfit.domain.dto.VisitLogUuidAndTimeDto;
import com.wfit.domain.entity.VisitLog;
import com.wfit.domain.entity.Visitor;
import com.wfit.mapper.VisitLogMapper;
import com.wfit.service.VisitService;
import com.wfit.utils.IpAddressUtils;
import com.wfit.utils.RedisCacheUtil;
import com.wfit.utils.UserAgentUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitLogMapper visitLogMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public Result visitLogs(String uuid, String[] date, Integer pageNum, Integer pageSize) {

        String beginTime = null;
        String endTime = null;

        if(date.length == 2){
            beginTime = date[0];
            endTime = date[1];
        }

        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);

        List<VisitLog> visitLogs = visitLogMapper.selectVisitLogs(uuid,beginTime,endTime);

        PageInfo<VisitLog> visitPageInfo = new PageInfo<VisitLog>(visitLogs);

        return Result.ok("请求成功",visitPageInfo);
    }

    @Override
    public void saveVisitorLog(Visitor visit) {

        visitLogMapper.saveVisitorLog(visit);

    }


    @Override
    @Transactional
    public void saveUserVisitorBlogLog(VisitLog visitLog, ProceedingJoinPoint joinPoint, long time) {


        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        VisitorLog annotation = method.getAnnotation(VisitorLog.class);

        Object[] args = joinPoint.getArgs();

        Boolean exists = redisCacheUtil.exists("visit:log", visitLog.getIp());
        if(exists){
            visitLog.setTimes(time);
            visitLog.setParam(JSONUtil.toJsonStr(args));
            visitLog.setBehavior(annotation.value());
            visitLog.setContent(annotation.content());
            visitLog.setRemark("blog");
            visitLog.setCreateTime(LocalDateTime.now());
            String uuid = visitLogMapper.selectVisitLogByIp(visitLog.getIp());
            visitLog.setUuid(uuid);
            visitLogMapper.saveVisitorBlogLog(visitLog);
            visitLogMapper.updateLastTime(LocalDateTime.now(),uuid);
        }

    }

    @Override
    public Result visitorsLogs(String[] date, Integer pageNum, Integer pageSize) {

        String beginTime = null;
        String endTime = null;

        if(date.length == 2){
            beginTime = date[0];
            endTime = date[1];
        }

        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        List<Visitor> visitors = visitLogMapper.selectVisitorsLogs(beginTime,endTime);
        PageInfo<Visitor> visitorPageInfo = new PageInfo<Visitor>(visitors);

        return Result.ok("请求成功",visitorPageInfo);
    }

    @Override
    @Transactional
    public Result deleteVisitorByIdAndUuid(Long id, String uuid) {

        visitLogMapper.deleteVisitorById(id);
        visitLogMapper.deleteVisitorByUUID(uuid);

        return Result.ok("删除成功");
    }

    @Override
    public Result deleteVisitLogById(Long id) {

        visitLogMapper.deleteVisitLogById(id);

        return Result.ok("删除成功");
    }

    @Override
    public List<VisitLogUuidAndTimeDto> getUUIDAndCreateTimeByYesterday() {

        return visitLogMapper.getUUIDAndCreateTimeByYesterday();
    }

    @Override
    public void saveVisitorRecord(Long uv, long pv, String yesterday) {

        visitLogMapper.saveVisitorRecord(uv,pv,yesterday);

    }

    @Override
    public void updateVisitor(String uuid, Long count) {

        visitLogMapper.updateVisitor(uuid,count);

    }
}
