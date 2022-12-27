package com.wfit.mapper;

import com.wfit.domain.entity.Moment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface MomentsMapper {
    /**
     * 查询全部动态个数
     * @return
     */
    Integer countMoment();

    /**
     * 分页查询动态
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<Moment> selectMomentsByPageNum(Integer pageNum, Integer pageSize);

    /**
     * 点赞存入数据库
     * @param id
     * @param ipAddr
     * @param now
     */
    void like(@Param("id") Long id, @Param("ip") String ipAddr, @Param("now") LocalDateTime now);

    /**
     * 点赞 + 1
     */
    void updateLike(Long id);

    /**
     * 添加动态
     * @param moment
     */
    void saveMoment(Moment moment);

    /**
     * 查询动态
     * @return
     */
    List<Moment> selectMoments();

    /**
     * 修改动态状态
     * @param id
     * @param published
     */
    void momentPublished(@Param("id") Long id,@Param("published") Boolean published);

    /**
     * 根据id查询动态
     * @param id
     * @return
     */
    Moment selectMomentById(Long id);

    /**
     * 修改动态
     * @param moment
     */
    void updateMoment(Moment moment);

    /**
     * 删除动态
     * @param id
     */
    void deleteMoment(Long id);
}
