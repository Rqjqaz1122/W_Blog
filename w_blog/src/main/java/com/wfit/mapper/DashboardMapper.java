package com.wfit.mapper;

import com.wfit.domain.dto.CityVisit;
import com.wfit.domain.dto.VisitRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DashboardMapper {

    /**
     * 城市访问量
     * @return
     */
    List<CityVisit> selectCityVisit();

    /**
     * 查询uv pv
     * @return
     */
    List<VisitRecord> pvAndUv();
}
