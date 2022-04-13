package com.alkemy.springboot_challenge.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GenreEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Long id;
    @Getter @Setter @Column(name = "name")
    private String name;
    @Getter @Setter @Column(name = "img")
    private String img;
    @Getter @Setter @ManyToMany
    @JoinTable(name = "genre_movie_match", joinColumns = @JoinColumn(name = "id_genre"), inverseJoinColumns = @JoinColumn(name = "id_movie"))
    private List<SeriesAndMoviesEntity> seriesAndMovies;
}
