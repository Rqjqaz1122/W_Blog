package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.dto.VisitLogUuidAndTimeDto;
import com.wfit.domain.entity.VisitLog;
import com.wfit.domain.entity.Visitor;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.List;

public interface VisitService {
    /**
     * 查询访问日志
     * @param uuid
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result visitLogs(String uuid, String[] date, Integer pageNum, Integer pageSize);

    /**
     * 访问日志
     * @param visit
     */
    void saveVisitorLog(Visitor visit);

    /**
     * 记录用户访问
     * @param request
     * @param joinPoint
     * @param l
     */
    void saveUserVisitorBlogLog(VisitLog request, ProceedingJoinPoint joinPoint, long l);

    /**
     * 查询访客日志
     * @param date
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result visitorsLogs(String[] date, Integer pageNum, Integer pageSize);

    /**
     * 根据id和uuid删除记录
     * @param id
     * @param uuid
     * @return
     */
    Result deleteVisitorByIdAndUuid(Long id, String uuid);

    /**
     * 根据id删除访问记录
     * @param id
     * @return
     */
    Result deleteVisitLogById(Long id);

    /**
     * 定时任务，查询昨天的所有访问记录
     * @return
     */
    List<VisitLogUuidAndTimeDto> getUUIDAndCreateTimeByYesterday();

    /**
     * 记录前一天的 uv 和 pv
     * @param uv
     * @param pv
     * @param yesterday
     */
    void saveVisitorRecord(Long uv, long pv, String yesterday);


    void updateVisitor(String uuid, Long count);
}
