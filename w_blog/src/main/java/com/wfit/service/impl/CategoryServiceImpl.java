package com.wfit.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wfit.constants.RedisConstants;
import com.wfit.domain.Result;
import com.wfit.domain.dto.BlogDto;
import com.wfit.domain.entity.Category;
import com.wfit.domain.entity.Tag;
import com.wfit.domain.vo.PageVo;
import com.wfit.mapper.BlogMapper;
import com.wfit.mapper.CategoryMapper;
import com.wfit.mapper.TagMapper;
import com.wfit.service.CategoryService;
import com.wfit.utils.RedisCacheUtil;
import com.wfit.utils.markdown.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCacheUtil redisCacheUtil;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Override
    public List<Category> categoryList() {


        List<Category> categoryList1 = redisCacheUtil.stringGetList(RedisConstants.CATEGORY_CACHE, Category.class);

        if(categoryList1 != null && !categoryList1.isEmpty()){
            return categoryList1;
        }

        List<Category> categoryList = categoryMapper.categoryList();
        //éæįžå­
        redisCacheUtil.setString(RedisConstants.CATEGORY_CACHE,categoryList);

        return categoryList;
    }

    @Override
    public Result findBlogsByCategoryName(String categoryName, Integer pageNum) {

        List<BlogDto> blogDtos = null;

        List<BlogDto> blogDtoList = redisCacheUtil.hashGetList(
                RedisConstants.BLOG_CATEGORY_NAME,
                String.valueOf(categoryName),
                BlogDto.class);

        if(blogDtoList != null && !blogDtoList.isEmpty()){
            //å―äļ­
            blogDtos = blogDtoList;
        }else{
            //æŠå―äļ­éæįžå­
            pageNum = (pageNum - 1) * 4;
            blogDtos = categoryMapper.selectBlogsCategoryName(categoryName,pageNum);

            if(blogDtos == null || blogDtos.isEmpty()){
                return Result.error("čŊĨæ į­ūäļæēĄææįŦ ");
            }

            blogDtos.forEach(o -> {
                o.setPrivacy(false);
                o.setReadTime(o.getWords()/200);
                o.setDescription(MarkdownUtils.markdownToHtmlExtensions(o.getDescription()));
            });
            redisCacheUtil.setHash(RedisConstants.BLOG_CATEGORY_NAME,categoryName,blogDtos);
        }
        //å°čĢčŪŋéŪé
        blogDtos.forEach(o -> o.setViews(redisCacheUtil.getVisit(o.getId())));

        Long count = categoryMapper.countByCategoryName(categoryName);

        double ceil = Math.ceil(count / 5.0);

        PageVo<BlogDto> blogDtoPageVo = new PageVo<>();
        blogDtoPageVo.setList(blogDtos);
        blogDtoPageVo.setTotalPage((long)ceil);

        return Result.ok("čŊ·æąæå",blogDtoPageVo);
    }

    @Override
    public Result categoryAndTag() {

        List<Category> categoryList = categoryMapper.categoryList();

        List<Tag> tagList = tagMapper.tagList();

        Map<String,Object> map = new HashMap<>();

        map.put("categories",categoryList);
        map.put("tags",tagList);

        return Result.ok("čŊ·æąæå",map);
    }

    @Override
    public Map<Long, Category> selectCategoryById(List<BlogDto> list) {

        List<Long> collect = list.stream()
                .map(BlogDto::getId)
                .collect(Collectors.toList());


        List<Category> categoryList = categoryMapper.selectCategoryById(collect);

        Map<Long,Category> categoryMap = new HashMap<>();

        for(int i = 0;i<collect.size();i++){
            Category category = categoryList.get(i);
            Long aLong = collect.get(i);
            categoryMap.put(aLong,category);
        }


        return categoryMap;
    }

    @Override
    public Result categories(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum,pageSize);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryMapper.categoryList());


        return Result.ok("č·åæå",pageInfo);
    }

    @Override
    @Transactional
    public Result deleteCategoryById(Long id) {


        Integer count = blogMapper.selectByCategoryIdCount(id);

        if(count > 0){
            return Result.error("åįąŧäļææįŦ äļč―å éĪ");
        }

        categoryMapper.deleteCategoryById(id);

        redisCacheUtil.deleteCache(RedisConstants.CATEGORY_CACHE);

        return Result.ok("å éĪæå");
    }

    @Override
    @Transactional
    public Result updateCategory(Category category) {

        categoryMapper.updateCategory(category);

        redisCacheUtil.deleteCache(RedisConstants.CATEGORY_CACHE);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_CATEGORY_NAME);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_CACHE);
        redisCacheUtil.deleteCache(RedisConstants.BLOG_PAGE_NUM);

        return Result.ok("äŋŪæđæå");
    }

    @Override
    public Result saveCategory(Category category) {

        categoryMapper.saveCategoryBlog(category);
        redisCacheUtil.deleteCache(RedisConstants.CATEGORY_CACHE);

        return Result.ok("æ°åĒæå");
    }
}
