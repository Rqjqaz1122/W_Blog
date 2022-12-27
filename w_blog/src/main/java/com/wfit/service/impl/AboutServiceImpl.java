package com.wfit.service.impl;

import cn.hutool.json.JSONUtil;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.entity.About;
import com.wfit.domain.vo.AboutVo;
import com.wfit.mapper.AboutMapper;
import com.wfit.service.AboutService;
import com.wfit.utils.RedisCacheUtil;
import com.wfit.utils.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AboutServiceImpl implements AboutService {

    @Autowired
    private AboutMapper aboutMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Override
    public Result aboutMe() {

        List<About> abouts = redisCacheUtil.stringGetList(RedisConstants.ABOUT_ME_CACHE, About.class);

        if(abouts == null || abouts.isEmpty()){
            //重构缓存
            abouts = aboutMapper.selectAboutMe();
            redisCacheUtil.setString(RedisConstants.ABOUT_ME_CACHE, JSONUtil.toJsonStr(abouts));
        }

        Map<String,Object> map = new HashMap<>();

        abouts.forEach( o ->{
            switch (o.getNameEn()){
                case "title": map.put("title",o.getValue());

                case "musicId": map.put("musicId",o.getValue());

                case "content":map.put("content", MarkdownUtils.markdownToHtmlExtensions(o.getValue()));
            }
        });
        map.put("commentEnabled",false);

        return Result.ok("获取成功",map);
    }

    @Override
    @Transactional
    public Result updateAbout(AboutVo aboutVo) {

        aboutMapper.updateAboutTitle(aboutVo.getTitle());
        aboutMapper.updateAboutContent(aboutVo.getContent());
        aboutMapper.updateAboutComment(aboutVo.getCommentEnabled());
        aboutMapper.updateAboutMusicId(aboutVo.getMusicId());

        redisCacheUtil.deleteCache(RedisConstants.ABOUT_ME_CACHE);
        return Result.ok("修改成功");
    }

    @Override
    public Result adminAboutMe() {

        List<About> abouts = aboutMapper.selectAboutMe();

        Map<String,Object> map = new HashMap<>();

        abouts.forEach( o ->{
            switch (o.getNameEn()){
                case "title": map.put("title",o.getValue());

                case "musicId": map.put("musicId",o.getValue());

                case "content":map.put("content", o.getValue());
            }
        });
        map.put("commentEnabled",false);

        return Result.ok("获取成功",map);
    }


}
