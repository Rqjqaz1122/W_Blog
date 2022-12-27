package com.wfit.domain.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * (SiteSetting)表实体类
 *
 * @author makejava
 * @since 2022-11-24 20:23:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteSetting  {

    private Long id;

    private String nameEn;
    
    private String nameZh;
    
    private String value;
    //1基础设置，2页脚徽标，3资料卡，4友链信息
    private Integer type;


}
