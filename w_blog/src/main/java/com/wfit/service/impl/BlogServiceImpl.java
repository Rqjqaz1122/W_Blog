package com.wfit.service.impl;

import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.config.LoginUser;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.dto.ArchiveDto;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.dto.RandomBlog;
import com.wfit.domain.dto.TagDto;
import com.wfit.domain.entity.Blog;
import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.Tag;
import com.wfit.domain.vo.*;
import com.wfit.mapper.BlogMapper;
import com.wfit.mapper.CategoryMapper;
import com.wfit.mapper.TagMapper;
import com.wfit.service.BlogService;
import com.wfit.service.TagService;
import com.wfit.utils.CopyBeanUtil;
import com.wfit.utils.RedisCacheUtil;
import com.wfit.utils.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<NewBlogVo> newBlogList() {


        List<NewBlogVo> newBlogVoList = redisCacheUtil.stringGetList(RedisConstants.NEW_BLOG_CACHE, NewBlogVo.class);

        if(newBlogVoList != null && !newBlogVoList.isEmpty()){
            return newBlogVoList;
        }

        //重构缓存
        List<NewBlogVo> blogVos = blogMapper.newBlogList();

        blogVos.forEach(o -> o.setPrivacy(false));
        redisCacheUtil.setString(RedisConstants.NEW_BLOG_CACHE,blogVos,72L,TimeUnit.HOURS);

        return blogVos;
    }

    @Override
    public List<RandomBlog> randomBlogList() {

        List<RandomBlog> randomBlogs1 = redisCacheUtil.stringGetList(RedisConstants.RANDOM_BLOG, RandomBlog.class);

        if(randomBlogs1 != null && !randomBlogs1.isEmpty()){
            return randomBlogs1;
        }

        //重构缓存
        List<RandomBlog> randomBlogs = blogMapper.randomBlogList();
        randomBlogs.forEach(o -> o.setPrivacy(false));
        redisCacheUtil.setString(RedisConstants.RANDOM_BLOG,randomBlogs,72L,TimeUnit.HOURS);

        return randomBlogs;
    }

    private static final Integer PAGE_SIZE = 5;
    @Override
    public PageVo<BlogDto> getBlogsInfoList(Integer pageNum) {

        int i = pageNum;

        //查询缓存
        List<BlogDto> blogDtos = redisCacheUtil.hashGetList(RedisConstants.BLOG_PAGE_NUM, String.valueOf(i), BlogDto.class);
        //未命中重建缓存
        if(blogDtos == null || blogDtos.isEmpty()){

            pageNum = (pageNum -1) * PAGE_SIZE;

            blogDtos = blogMapper.getBlogsInfoList(pageNum,PAGE_SIZE);

            Map<Long,List<Tag>> map =  tagService.selectTagsByBlogsId(blogDtos);

            blogDtos.forEach(o -> {
                o.setPrivacy(false);
                o.setTags(map.get(o.getId()));
                o.setDescription(MarkdownUtils.markdownToHtmlExtensions(o.getDescription()));
                o.setReadTime(o.getWords()/200);
            });
            redisCacheUtil.setHash(RedisConstants.BLOG_PAGE_NUM,String.valueOf(i),blogDtos);
        }
        //从redis中查询浏览量
        blogDtos.forEach(o -> {
            Long visit = redisCacheUtil.getVisit(o.getId());
            if(visit != null){
                o.setViews(visit);
            }
        });
        //查询文章个数
        Integer count = blogMapper.count();
        //封装数据
        double ceil = Math.ceil(count / 5.0);

        PageVo<BlogDto> pageVo = new PageVo<>();
        pageVo.setList(blogDtos);
        pageVo.setTotalPage((long)ceil);

        return pageVo;
    }

    @Override
    public Result findBlogById(Long id) {

        BlogVo blogVo = redisCacheUtil.hashGetBean(RedisConstants.BLOG_CACHE, id.toString(), BlogVo.class);

        if(Objects.isNull(blogVo)){
            //未命中重构缓存
            blogVo = blogMapper.selectBlogById(id);
            redisCacheUtil.setHash(RedisConstants.BLOG_CACHE,id.toString(),JSONUtil.toJsonStr(blogVo));
        }

        if(blogVo == null){
            return Result.error("文章不存在");
        }

        blogVo.setContent(MarkdownUtils.markdownToHtmlExtensions(blogVo.getContent()));

        Long visit = redisCacheUtil.getVisit(id);

        if(visit != null){
            blogVo.setViews(visit);
        }


        return Result.ok("获取成功",blogVo);
    }

    @Override
    public Result findArchives() {

        List<ArchiveDto> archiveDtos = redisCacheUtil.stringGetList(RedisConstants.ARCHIVE_CACHE, ArchiveDto.class);

        if(archiveDtos == null || archiveDtos.isEmpty()){

            //未命中查询数据库重构缓存

            archiveDtos = blogMapper.findArchives();
            redisCacheUtil.setString(RedisConstants.ARCHIVE_CACHE,JSONUtil.toJsonStr(archiveDtos));
        }

        //归档数量
        int count = archiveDtos.size();

        archiveDtos.forEach(o -> {
            String date = o.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy年MM月"));
            o.setDate(date);
            o.setDay(o.getCreateTime().getDayOfMonth() + "日");
        });

        List<ArchiveVo> archiveVos = CopyBeanUtil.copyList(archiveDtos, ArchiveVo.class);
        archiveVos.forEach(o -> o.setPrivacy(false));

        Map<String, List<ArchiveVo>> collect = archiveVos.stream()
                .collect(Collectors.groupingBy(ArchiveVo::getDate));

        BlogMapVo<ArchiveVo> blogMapVo = new BlogMapVo<>();

        blogMapVo.setBlogMap(collect);
        blogMapVo.setCount(count);

        return Result.ok("请求成功",blogMapVo);
    }

    @Override
    @Transactional
    public Result saveBlog(SaveBlogVo saveBlogVo) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();

        Long userId = loginUser.getUser().getId();
        saveBlogVo.setUserId(userId);
        saveBlogVo.setCreateTime(LocalDateTime.now());
        saveBlogVo.setUpdateTime(LocalDateTime.now());

        Object cate = saveBlogVo.getCate();

        Category category = new Category();

        if(cate instanceof String){
            category.setName((String) cate);
            categoryMapper.saveCategoryBlog(category);
            saveBlogVo.setCate(category.getId());
            redisCacheUtil.deleteCache(RedisConstants.CATEGORY_CACHE);
        }

        blogMapper.saveBlog(saveBlogVo);

        tagService.saveTagsByBlog(saveBlogVo.getTagList(),saveBlogVo.getId());

        redisCacheUtil.deleteCache(RedisConstants.BLOG_PAGE_NUM);
        redisCacheUtil.deleteCache(RedisConstants.RANDOM_BLOG);
        redisCacheUtil.deleteCache(RedisConstants.NEW_BLOG_CACHE);

        return Result.ok("发布成功");
    }

    @Override
    public Result adminBlogs(String title, Long categoryId, Integer pageNum, Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        String orderBy = "create_time desc";
        PageHelper.startPage(pageNum,pageSize,orderBy);
        PageInfo<Blog> pageInfo = new PageInfo<Blog>(getBlogsByTitleOrCategoryId(title,categoryId));

        List<Category> categoryList = categoryMapper.categoryList();
        map.put("blogs",pageInfo);
        map.put("categories",categoryList);

        return Result.ok("请求成功",map);
    }

    @Override
    @Transactional
    public Result blogTop(Long id, Boolean top) {


        int t = 0;
        // 如果 top 为 true 则 t = 1
        if(top){
            t = 1;
        }

        blogMapper.blogTop(id,t);

        redisCacheUtil.deleteCache(RedisConstants.BLOG_PAGE_NUM);

        return Result.ok("修改成功");
    }

    @Override
    @Transactional
    public Result blogCommend(Long id, Boolean recommend) {

        int r = 0;

        if(recommend){
            r = 1;
        }

        blogMapper.blogCommend(id,r);

        redisCacheUtil.deleteCache(RedisConstants.RANDOM_BLOG);

        return Result.ok("修改成功");
    }

    @Override
    @Transactional
    public Result blogVisibility(Long id, VisibilityVo visibilityVo) {

        blogMapper.blogVisibility(id,visibilityVo);
        redisCacheUtil.deleteCache(RedisConstants.RANDOM_BLOG);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_PAGE_NUM);
        redisCacheUtil.deleteCache(RedisConstants.NEW_BLOG_CACHE);

        return Result.ok("修改成功");
    }

    @Override
    @Transactional
    public Result updateBlog(SaveBlogVo saveBlogVo) {

        Object cate = saveBlogVo.getCate();

        if(cate instanceof String){
            Category category = new Category();
            category.setName((String) cate);
            categoryMapper.saveCategoryBlog(category);
            saveBlogVo.setCate(category.getId());
            redisCacheUtil.deleteCache(RedisConstants.CATEGORY_CACHE);
        }

        List<Object> tagList = saveBlogVo.getTagList();
        //删除对应的标签
        tagMapper.deleteTagByBlogId(saveBlogVo.getId());

        tagService.saveTagsByBlog(tagList,saveBlogVo.getId());

        saveBlogVo.setUpdateTime(LocalDateTime.now());

        blogMapper.updateBlog(saveBlogVo);

        redisCacheUtil.deleteHash(RedisConstants.BLOG_CACHE,saveBlogVo.getId().toString());

        deleteCommentCache();

        return Result.ok("修改成功");
    }

    @Override
    public Result deleteBlogById(Long id) {

        blogMapper.deleteBlogById(id);

        tagMapper.deleteTagByBlogId(id);
        //清除缓存
        deleteCommentCache();

        return Result.ok("删除成果");
    }

    @Override
    public List<Long> selectBlogsId() {

        return blogMapper.selectBlogsId();
    }

    @Override
    @Transactional
    public void updateBlogViews(List<Long> ids, List<Long> visits) {

        for(int i=0;i<ids.size();i++){
            blogMapper.updateBlogView(ids.get(i),visits.get(i));
        }
    }

    @Override
    public Result getAdminBlogsById(Long id) {

        BlogVo blogVo = blogMapper.selectBlogById(id);

        if(blogVo == null){
            return Result.error("没有该文章");
        }

        return Result.ok("请求成功",blogVo);
    }


    private List<Blog> getBlogsByTitleOrCategoryId(String title, Long categoryId) {

       return blogMapper.getBlogsByTitleOrCategoryId(title,categoryId);
    }

    private void deleteCommentCache(){
        redisCacheUtil.deleteCache(RedisConstants.BLOG_PAGE_NUM);
        redisCacheUtil.deleteCache(RedisConstants.ARCHIVE_CACHE);
        redisCacheUtil.deleteCache(RedisConstants.TAG_BLOGS);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_CACHE);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_CATEGORY_NAME);
        redisCacheUtil.deleteCache(RedisConstants.NEW_BLOG_CACHE);
        redisCacheUtil.deleteCache(RedisConstants.RANDOM_BLOG);
    }


}
