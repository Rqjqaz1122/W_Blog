package com.wfit.task;

import com.wfit.constants.RedisConstants;
import com.wfit.domain.dto.VisitLogUuidAndTimeDto;
import com.wfit.service.VisitService;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Component
public class VisitorSyncScheduleTask {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private VisitService visitService;

    private static final Long ONE_DAY_TIME = -24 * 60 * 60 * 1000L;


    public void syncVisitInfoToDatabase(){

        Long uv = redisCacheUtil.getNumber("visit:log");

        //清空redis缓存中的访客ip
        redisCacheUtil.deleteCache("visit:log");
        List<VisitLogUuidAndTimeDto> logUuidAndTimeDtos = visitService.getUUIDAndCreateTimeByYesterday();

        long pv = 0L;
        for (VisitLogUuidAndTimeDto logUuidAndTimeDto : logUuidAndTimeDtos) {
            pv = pv + logUuidAndTimeDto.getCount();
        }

        String yesterday = LocalDateTime.now().plus(ONE_DAY_TIME, ChronoUnit.MILLIS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //保存前一天uv pv
        visitService.saveVisitorRecord(uv,pv,yesterday);

        logUuidAndTimeDtos.forEach(o -> visitService.updateVisitor(o.getUuid(),o.getCount()));


    }

}
