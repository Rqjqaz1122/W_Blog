package com.wfit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.entity.Moment;
import com.wfit.domain.vo.PageVo;
import com.wfit.mapper.MomentsMapper;
import com.wfit.service.MomentsService;
import com.wfit.utils.IpAddressUtils;
import com.wfit.utils.RedisCacheUtil;
import com.wfit.utils.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MomentsServiceImpl implements MomentsService {

    @Autowired
    private MomentsMapper momentsMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;
    private static final Integer PAGE_SIZE = 5;

    @Override
    public Result selectMomentsByPageNum(Integer pageNum) {

        int i = pageNum;
        List<Moment> moments = null;

        moments = redisCacheUtil.hashGetList(RedisConstants.MOMENT_CACHE, String.valueOf(i), Moment.class);

        if(moments == null || moments.isEmpty()){
            //未命中重构缓存
            moments = momentsMapper.selectMomentsByPageNum(pageNum,PAGE_SIZE);
            redisCacheUtil.setHash(RedisConstants.MOMENT_CACHE,String.valueOf(i),moments);
        }

        Integer count = momentsMapper.countMoment();

        moments.forEach(o -> o.setContent(MarkdownUtils.markdownToHtmlExtensions(o.getContent())));

        PageVo<Moment> pageVo = new PageVo<>();
        pageVo.setList(moments);
        double ceil = Math.ceil(count / 5.0);
        pageVo.setTotalPage((long)ceil);

        return Result.ok("获取成功",pageVo);
    }

    @Override
    public Result like(Long id, HttpServletRequest request) {

        String ipAddr = IpAddressUtils.getIpAddress(request);
        Boolean exists = redisCacheUtil.exists(RedisConstants.MOMENT_LIKE + id, ipAddr);

        if(Boolean.TRUE.equals(exists)){
            return Result.create(401,"请勿重复点赞");
        }

        redisCacheUtil.setSet(RedisConstants.MOMENT_LIKE + id,ipAddr);
        //异步写入数据库
        new Thread(() ->{
            momentsMapper.like(id,ipAddr, LocalDateTime.now());
            momentsMapper.updateLike(id);
        }).start();

        redisCacheUtil.deleteCache(RedisConstants.MOMENT_CACHE);

        return Result.ok("点赞成功");
    }

    @Override
    public Result moment(Moment moment) {

        if(moment.getCreateTime() == null){
            moment.setCreateTime(LocalDateTime.now());
        }

        momentsMapper.saveMoment(moment);

        redisCacheUtil.deleteCache(RedisConstants.MOMENT_CACHE);
        return Result.ok("发布成功");
    }

    @Override
    public Result moments(Integer pageNum, Integer pageSize) {

        String orderBy = "create_time DESC";

        PageHelper.startPage(pageNum,pageSize,orderBy);
        PageInfo<Moment> momentPageInfo = new PageInfo<Moment>(momentsMapper.selectMoments());

        return Result.ok("请求成功",momentPageInfo);
    }

    @Override
    public Result momentPublished(Long id, Boolean published) {

        momentsMapper.momentPublished(id,published);

        redisCacheUtil.deleteCache(RedisConstants.MOMENT_CACHE);

        return Result.ok("修改成功");
    }

    @Override
    public Result selectMomentById(Long id) {

        Moment moment = momentsMapper.selectMomentById(id);

        if(moment == null){
            return Result.error("没有该动态");
        }

        return Result.ok("获取成功",moment);
    }

    @Override
    public Result updateMoment(Moment moment) {

        momentsMapper.updateMoment(moment);

        redisCacheUtil.deleteCache(RedisConstants.MOMENT_CACHE);

        return Result.ok("修改成功");
    }

    @Override
    public Result deleteMoment(Long id) {

        momentsMapper.deleteMoment(id);

        redisCacheUtil.deleteCache(RedisConstants.MOMENT_CACHE);

        return Result.ok("删除成功");
    }

}
