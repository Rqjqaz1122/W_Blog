package com.wfit.mapper;

import com.wfit.domain.dto.VisitLogUuidAndTimeDto;
import com.wfit.domain.entity.VisitLog;
import com.wfit.domain.entity.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface VisitLogMapper {

    /**
     * 查询访问日志
     * @param uuid
     * @param beginTime
     * @param endTime
     * @return
     */
    List<VisitLog> selectVisitLogs(@Param("uuid") String uuid, @Param("bt") String beginTime, @Param("et") String endTime);

    void saveVisitorLog(Visitor visit);

    /**
     * 根据ip查询uuid
     * @param ipAddress
     * @return
     */
    String selectVisitLogByIp(String ipAddress);

    /**
     * 访问日志
     * @param visitLog
     */
    void saveVisitorBlogLog(VisitLog visitLog);

    /**
     * 修改最后访问时间
     * @param now
     * @param uuid
     */
    void updateLastTime(@Param("now") LocalDateTime now,@Param("uuid") String uuid);

    /**
     * 查询访客记录
     * @param beginTime
     * @param endTime
     * @return
     */
    List<Visitor> selectVisitorsLogs(@Param("bt") String beginTime, @Param("et") String endTime);

    /**
     * 删除访客记录
     * @param id
     */
    void deleteVisitorById(Long id);

    /**
     * 删除访客浏览记录
     * @param id
     */
    void deleteVisitorByUUID(String id);

    /**
     * 根据id删除访客浏览记录
     * @param id
     */
    void deleteVisitLogById(Long id);

    /**
     * 查询前一天的所有访问记录
     * @return
     */
    List<VisitLogUuidAndTimeDto> getUUIDAndCreateTimeByYesterday();


    /**
     * 保存前一天的uv pv
     * @param uv
     * @param pv
     * @param yesterday
     */
    void saveVisitorRecord(@Param("uv") Long uv, @Param("pv") long pv, @Param("yesterday") String yesterday);

    /**
     * 更新访客pv
     * @param uuid
     * @param count
     */
    void updateVisitor(@Param("uuid") String uuid, @Param("count") Long count);
}
