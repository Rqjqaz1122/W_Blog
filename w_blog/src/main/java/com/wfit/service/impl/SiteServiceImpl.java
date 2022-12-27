package com.wfit.service.impl;

import cn.hutool.json.JSONUtil;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.dto.CopyRight;
import com.wfit.domain.dto.Favorites;
import com.wfit.domain.dto.RandomBlog;
import com.wfit.domain.dto.Site;
import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.SiteSetting;
import com.wfit.domain.entity.Tag;
import com.wfit.domain.vo.NewBlogVo;
import com.wfit.domain.vo.SiteSettingVo;
import com.wfit.domain.vo.SiteVo;
import com.wfit.mapper.FriendMapper;
import com.wfit.mapper.SiteMapper;
import com.wfit.service.BlogService;
import com.wfit.service.CategoryService;
import com.wfit.service.SiteService;
import com.wfit.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteMapper siteMapper;

    @Autowired
    private BlogService blogService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final Pattern PATTERN = Pattern.compile("\"(.*?)\"");

    @Override
    public Result getSite() {

        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        List<SiteSetting> siteSettings = null;

        String siteJsonStr = ops.get(RedisConstants.SITE_SETTINGS_CACHE);

        if(StringUtils.hasText(siteJsonStr)){
            siteSettings = JSONUtil.toList(siteJsonStr,SiteSetting.class);
        }else{
            //重构缓存
            siteSettings = siteMapper.selectSite();
            ops.set(RedisConstants.SITE_SETTINGS_CACHE,JSONUtil.toJsonStr(siteSettings));
        }

        List<NewBlogVo> newBlogVoList = blogService.newBlogList();

        List<Category> categoryList = categoryService.categoryList();

        List<Tag> tagList = tagService.tagList();

        List<RandomBlog> randomBlogs = blogService.randomBlogList();


        Map<Integer, List<SiteSetting>> listMap = siteSettings
                .stream()
                .collect(Collectors.groupingBy(SiteSetting::getType));

        List<SiteSetting> siteSettings1 = listMap.get(1);
        List<SiteSetting> siteSettings2 = listMap.get(2);
        List<SiteSetting> siteSettings3 = listMap.get(3);

        List<Site> badges = siteSettings3
                .stream()
                .map(o -> JSONUtil.toBean(o.getValue(), Site.class)
                ).collect(Collectors.toList());

        Map<String,Object> siteInfo = new HashMap<>();
        siteSettings1.forEach(o ->{
            String nameEn = o.getNameEn();
            String value = o.getValue();
            if("copyright".equals(nameEn)){
                CopyRight copyRight = JSONUtil.toBean(value, CopyRight.class);
                siteInfo.put(nameEn,copyRight);

            }else {
                siteInfo.put(nameEn,value);
            }
        });


        Map<String,Object> introduction = new HashMap<>();
        List<Favorites> favourites = new ArrayList<>();

        List<String> list = new ArrayList<>();
        for (SiteSetting siteSetting : siteSettings2) {

            String nameEn = siteSetting.getNameEn();
            if("favorite".equals(nameEn)){
                String value = siteSetting.getValue();
                Favorites favorites = JSONUtil.toBean(value, Favorites.class);
                favourites.add(favorites);
            }
            if("rollText".equals(nameEn)){
                String value = siteSetting.getValue();
                Matcher m = PATTERN.matcher(value);
                while (m.find()) {
                    list.add(m.group(1));
                }
            }

        }
        siteSettings2.forEach(o->{
            String nameEn = o.getNameEn();
            String value = o.getValue();
            if("favorite".equals(nameEn)){
                introduction.put("favorites",favourites);
            } else if("rollText".equals(nameEn)) {
                introduction.put("rollText", list);
            }else {
                introduction.put(nameEn,value);
            }
        });


        SiteVo siteVo = new SiteVo();
        siteVo.setBadges(badges);
        siteVo.setSiteInfo(siteInfo);
        siteVo.setIntroduction(introduction);
        siteVo.setNewBlogList(newBlogVoList);
        siteVo.setCategoryList(categoryList);
        siteVo.setTagList(tagList);
        siteVo.setRandomBlogList(randomBlogs);

        return Result.ok("请求成功",siteVo);
    }

    @Override
    public Result siteSettings() {
        List<SiteSetting> siteSettings = siteMapper.selectSite();

        Map<Integer, List<SiteSetting>> collect = siteSettings.stream()
                .collect(Collectors.groupingBy(SiteSetting::getType));


        Map<String,List<SiteSetting>> map = new HashMap<>();

        map.put("type1",collect.get(1));
        map.put("type2",collect.get(2));
        map.put("type3",collect.get(3));


        return Result.ok("获取成功",map);
    }

    @Override
    @Transactional
    public Result updateSiteSettings(SiteSettingVo siteSettingVo) {

        List<Long> deleteIds = siteSettingVo.getDeleteIds();

        if(!deleteIds.isEmpty()){
           Integer i = siteMapper.deleteSiteSettingsByIds(deleteIds);
           if(i < 1){
               throw new RuntimeException("删除siteSetting失败");
           }
        }


        Integer i = siteMapper.updateSiteSettings(siteSettingVo.getSettings());

        if(i < 1){
            throw new RuntimeException("修改siteSetting失败");
        }


        return Result.ok("修改成功");
    }


}
