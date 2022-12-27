package com.wfit.domain.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveVo {

    private Long id;

    private String day;

    private String title;

    private Boolean privacy;

    @JsonIgnore
    private String date;
}
