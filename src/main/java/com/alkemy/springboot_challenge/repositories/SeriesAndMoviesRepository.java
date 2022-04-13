package com.alkemy.springboot_challenge.repositories;

import com.alkemy.springboot_challenge.entities.SeriesAndMoviesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SeriesAndMoviesRepository extends JpaRepository<SeriesAndMoviesEntity, Long> {


    /*  No sé bien cómo filtrar el orden cuando venga vacío. Parámetros se pasan como valores, no se puede escribir SQL con parámetros.
        Intenté usar .sort(new Comparator) en el Controller para ordenarlo allá, pero sin éxito. Tengo que seguir investigando.
     */
    @Query(value = "SELECT * FROM series_and_movies SAM " +
            "LEFT JOIN genre_movie_match GMM ON SAM.id = GMM.id_movie " +
            "WHERE (:title IS NULL OR SAM.title = :title) AND " +
            "(:genre IS NULL OR GMM.id_genre = :genre)", nativeQuery = true)
    List<SeriesAndMoviesEntity> findByParams(@Param("title") String title,
                                             @Param("genre") String genre);
}
