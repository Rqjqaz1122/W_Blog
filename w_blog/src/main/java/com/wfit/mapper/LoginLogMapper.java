package com.wfit.mapper;

import com.wfit.domain.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LoginLogMapper {
    /**
     * 新增登录日志
     * @param loginLog
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 查询日志
     * @param beginTime
     * @param endTime
     * @return
     */
    List<LoginLog> selectLoginLogsByDate(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

    /**
     * 删除登录日志
     * @param id
     */
    void deleteLoginLog(Long id);
}
