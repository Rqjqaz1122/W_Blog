package com.wfit.domain.vo;

import com.wfit.domain.entity.SiteSetting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SiteSettingVo {

    private List<Long> deleteIds;

    private List<SiteSetting> settings;

}
