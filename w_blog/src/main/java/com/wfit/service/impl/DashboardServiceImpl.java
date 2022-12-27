package com.wfit.service.impl;

import com.wfit.domain.Result;
import com.wfit.domain.dto.CityVisit;
import com.wfit.domain.dto.SeriesDto;
import com.wfit.domain.dto.VisitRecord;
import com.wfit.mapper.BlogMapper;
import com.wfit.mapper.CategoryMapper;
import com.wfit.mapper.DashboardMapper;
import com.wfit.mapper.TagMapper;
import com.wfit.service.BlogService;
import com.wfit.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardMapper dashboardMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Result dashboard() {

        LocalDateTime now = LocalDateTime.now();

        Map<String,Object> map = new HashMap<>();
        //所有pv uv
        List<VisitRecord> visitRecords = dashboardMapper.pvAndUv();

        List<String> date = new ArrayList<>(visitRecords.size());
        List<Integer> uv = new ArrayList<>(visitRecords.size());
        List<Integer> pv = new ArrayList<>(visitRecords.size());

        Map<String,Object> visit = new HashMap<>();
        visit.put("date",date);
        visit.put("uv",uv);
        visit.put("pv",pv);

        visitRecords.forEach(o -> {
            date.add(o.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            uv.add(o.getUv());
            pv.add(o.getPv());
        });


        //文章数
        Integer blogCount = blogMapper.count();
        //城市访问量
        List<CityVisit> cityVisits = dashboardMapper.selectCityVisit();
        //查询标签
        Map<String,Object> tag = getTagsList();
        //查询分类
        Map<String,Object> category = getCategoryList();

        map.put("uv",0);
        map.put("blogCount",blogCount);
        map.put("pv",0);
        map.put("tag",tag);
        map.put("cityVisitor",cityVisits);
        map.put("category",category);
        map.put("visitRecord",visit);
        map.put("commentCount",0);

        return Result.ok("获取成功",map);
    }

    private Map<String, Object> getCategoryList() {

        Map<String,Object> map = new HashMap<>();

        List<SeriesDto> seriesDtos = categoryMapper.selectCategoryList();

        List<String> legend = seriesDtos.stream().map(SeriesDto::getName)
                .collect(Collectors.toList());

        map.put("legend",legend);
        map.put("series",seriesDtos);

        return map;
    }

    private Map<String, Object> getTagsList() {

        Map<String,Object> map = new HashMap<>();

        List<SeriesDto> seriesDtos = tagMapper.selectTag();

        List<String> legend = seriesDtos
                .stream()
                .map(SeriesDto::getName)
                .collect(Collectors.toList());

        map.put("legend",legend);
        map.put("series",seriesDtos);

        return map;
    }
}
