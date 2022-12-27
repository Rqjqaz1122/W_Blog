package com.wfit.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.dto.TagDto;
import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.Tag;
import com.wfit.domain.vo.PageVo;
import com.wfit.mapper.TagMapper;
import com.wfit.service.CategoryService;
import com.wfit.service.TagService;
import com.wfit.utils.CopyBeanUtil;
import com.wfit.utils.RedisCacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Tag> tagList() {

        List<Tag> tags = redisCacheUtil.stringGetList(RedisConstants.TAG_CACHE, Tag.class);

        if(tags != null && !tags.isEmpty()){
            return tags;
        }

        List<Tag> tagList = tagMapper.tagList();
        //未命中重构缓存
        redisCacheUtil.setString(RedisConstants.TAG_CACHE,JSONUtil.toJsonStr(tagList));

        return tagList;
    }

    @Override
    public void saveTagsByBlog(List<Object> tagList, Long id) {

        List<Tag> tags = new ArrayList<>();

        List<Tag> tags1 = new ArrayList<>();

        for (Object o : tagList) {
            Tag tag = new Tag();
            if (o instanceof String) {
                tag.setName((String) o);
                tags1.add(tag);
            } else {
                Integer tagId = (Integer) o;
                tag.setId(tagId.longValue());
                tags.add(tag);
            }
        }

        if(!tags1.isEmpty()){
            tagMapper.saveTag(tags1);
            tags.addAll(tags1);
            redisCacheUtil.deleteCache(RedisConstants.TAG_CACHE);
        }

        tagMapper.saveTagsByBlog(tags,id);
    }

    @Override
    public Map<Long, List<Tag>> selectTagsByBlogsId(List<BlogDto> blogDtos) {

        List<TagDto> tagDtos = tagMapper.selectAllTagsByBlogs();


        Map<Long, List<TagDto>> collect = tagDtos.stream()
                .collect(Collectors.groupingBy(TagDto::getBlogId));

        Map<Long,List<Tag>> map = new HashMap<>();

        for (Map.Entry<Long, List<TagDto>> e : collect.entrySet()) {
            Long key = e.getKey();
            List<TagDto> value = e.getValue();
            List<Tag> tags = CopyBeanUtil.copyList(value, Tag.class);
            map.put(key,tags);
        }

        return map;
    }

    @Override
    public Result getTagNameByBlogs(String tagName) {

        PageVo pageVo1 = redisCacheUtil.hashGetBean(RedisConstants.TAG_BLOGS, tagName, PageVo.class);

        if(pageVo1 != null){
            return Result.ok("获取成功",pageVo1);
        }

        List<BlogDto> list = tagMapper.selectTagNameByBlogs(tagName);

        Integer count = tagMapper.selectTagNameByBlogsCount(tagName);


        Map<Long, Category> category = categoryService.selectCategoryById(list);

        List<Long> collect = list.stream()
                .map(BlogDto::getId)
                .collect(Collectors.toList());

        List<TagDto> tagDtos = tagMapper.selectTagById(collect);

        Map<Long, List<TagDto>> collect1 = tagDtos.stream()
                .collect(Collectors.groupingBy(TagDto::getBlogId));

        Map<Long,List<Tag>> map = new HashMap<>();

        for (Map.Entry<Long, List<TagDto>> e : collect1.entrySet()) {
            Long key = e.getKey();
            List<TagDto> value = e.getValue();
            List<Tag> tags = CopyBeanUtil.copyList(value, Tag.class);
            map.put(key,tags);
        }

        list.forEach(o ->{
            List<Tag> tags = map.get(o.getId());
            o.setTags(tags);
            o.setPrivacy(false);
            o.setCategory(category.get(o.getId()));
        });

        PageVo<BlogDto> pageVo = new PageVo<>();
        pageVo.setList(list);
        double ceil = Math.ceil(count / 5.0);
        pageVo.setTotalPage((long) ceil);

        redisCacheUtil.setHash(RedisConstants.TAG_BLOGS,tagName,JSONUtil.toJsonStr(pageVo));

        return Result.ok("获取成功",pageVo);
    }

    @Override
    public Result selectTagsByPageNumAndPageSize(Integer pageNum, Integer pageSize) {


        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Tag> pageInfo = new PageInfo<Tag>(tagMapper.tagList());


        return Result.ok("获取成功",pageInfo);
    }

    @Override
    public Result saveTag(Tag tag) {

        tagMapper.save(tag);
        deleteCommentCache();
        return Result.ok("新增成功");
    }

    @Override
    public Result updateTag(Tag tag) {

        tagMapper.updateTag(tag);
        deleteCommentCache();

        return Result.ok("修改成功");
    }

    @Override
    public Result deleteTagById(Long id) {

        Integer count = tagMapper.selectTagByIdCount(id);

        if(count > 0){
            return Result.error("标签下有文章，不可删除");
        }

        tagMapper.deleteByTagId(id);
        deleteCommentCache();

        return Result.ok("删除成功");
    }

    private void deleteCommentCache(){

        redisCacheUtil.deleteCache(RedisConstants.TAG_CACHE);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_PAGE_NUM);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_CATEGORY_NAME);

    }



}
