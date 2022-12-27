package com.wfit.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArchiveDto {

    private Long id;

    private String title;

    private String day;

    private LocalDateTime createTime;

    private String date;

}
