package com.alkemy.springboot_challenge.dto;

import com.alkemy.springboot_challenge.entities.CharacterEntity;
import com.alkemy.springboot_challenge.entities.GenreEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SeriesAndMoviesDTODetails {

    @Setter @Getter
    private Long id;
    @Setter @Getter
    private String img;
    @Setter @Getter
    private String title;
    @Setter @Getter
    private int premiereDate;
    @Setter @Getter
    private int rating;
    @Setter @Getter
    private List<CharacterEntity> characters;
    @Setter @Getter
    private List<GenreEntity> genre;
}
