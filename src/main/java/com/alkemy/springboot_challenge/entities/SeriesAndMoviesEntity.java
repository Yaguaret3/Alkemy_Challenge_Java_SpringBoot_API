package com.alkemy.springboot_challenge.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "series_and_movies")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SeriesAndMoviesEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Long id;
    @Getter @Setter @Column(name = "img")
    private String img;
    @Getter @Setter @Column(name = "title")
    private String title;
    @Getter @Setter @Column(name = "premiere_date")
    private int premiereDate;
    @Getter @Setter @Column(name = "rating")
    private int rating;
    @Getter @Setter @ManyToMany(mappedBy = "seriesAndMovies")
    private List<CharacterEntity> characters;
    @Getter @Setter @ManyToMany(mappedBy = "seriesAndMovies")
    private List<GenreEntity> genre;
}
