package com.wfit.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Site {

    private String title;

    private String url;

    private String subject;

    private String value;

    private String color;

}
