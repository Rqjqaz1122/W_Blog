package com.wfit.service;

import com.wfit.domain.Result;
import com.wfit.domain.entity.Moment;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletRequest;

public interface MomentsService {
    /**
     * 分页查询动态
     * @param pageNum
     * @return
     */
    Result selectMomentsByPageNum(Integer pageNum);

    /**
     * 点赞
     * @param id
     * @param request
     * @return
     */
    Result like(Long id, HttpServletRequest request);

    /**
     * 动态
     * @param moment
     * @return
     */
    Result moment(Moment moment);

    /**
     * 分页查询动态
     * @param pageNum
     * @param pageSize
     * @return
     */
    Result moments( Integer pageNum, Integer pageSize);

    /**
     * 修改动态可见状态
     * @param id
     * @param published
     * @return
     */
    Result momentPublished(Long id, Boolean published);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    Result selectMomentById(Long id);

    /**
     * 修改动态
     * @param moment
     * @return
     */
    Result updateMoment(Moment moment);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    Result deleteMoment(Long id);
}
