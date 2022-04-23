package com.alkemy.springboot_challenge.dto;

import lombok.Getter;
import lombok.Setter;

public class SeriesAndMoviesDTOGeneral {

    @Setter @Getter
    private Long id;
    @Setter @Getter
    private String img;
    @Setter @Getter
    private String title;
    @Setter @Getter
    private int premiereDate;
}
