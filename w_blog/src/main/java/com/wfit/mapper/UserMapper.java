package com.wfit.mapper;

import com.wfit.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    User selectByUsername(String username);

}
