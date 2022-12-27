package com.wfit.task;

import com.wfit.service.BlogService;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RedisSyncScheduleTask {

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private BlogService blogService;

    public void syncBlogViewsToDatabase(){

        List<Long> ids = blogService.selectBlogsId();

        List<Long> visits = new ArrayList<>();

        for (Long id : ids) {
            Long visit = redisCacheUtil.getVisit(id);
            visits.add(visit);
        }

        blogService.updateBlogViews(ids,visits);
    }


}
