package com.alkemy.springboot_challenge.dto;

import com.alkemy.springboot_challenge.entities.SeriesAndMoviesEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CharacterDTODetails {

    @Setter @Getter
    private Long id;
    @Setter @Getter
    private String img;
    @Setter @Getter
    private String name;
    @Setter @Getter
    private int age;
    @Setter @Getter
    private String weight;
    @Setter @Getter
    private String story;
    @Setter @Getter
    private List<SeriesAndMoviesEntity> seriesAndMovies;
}
