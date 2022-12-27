package com.wfit.mapper;

import com.wfit.domain.entity.About;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AboutMapper {
    /**
     * 关于我
     * @return
     */
    List<About> selectAboutMe();

    void updateAboutTitle(String title);

    void updateAboutContent(String content);

    void updateAboutComment(String commentEnabled);

    void updateAboutMusicId(String musicId);
}
