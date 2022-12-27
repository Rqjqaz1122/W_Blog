package com.wfit.domain.dto;

import lombok.Data;

@Data
public class TagDto {

    private Long id;

    private Long blogId;

    private String name;

    private String color;

}
