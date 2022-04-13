package com.alkemy.springboot_challenge.repositories;

import com.alkemy.springboot_challenge.entities.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Long> {

    /* Aquí debería encontrar la manera de que la búsqueda me devuelva SOLAMENTE las columnas solicitadas (img & name).
       Usando Entities se me complica, porque me pide que rellene todos los campos.
       Intenté usar una clase adHoc sin anotaciones y pasarle los datos, pero me dio error 406 Not Acceptable.
     */

    @Query(value = "SELECT * FROM characters C " +
            "LEFT JOIN character_movie_match CMM ON C.id = CMM.id_character " +
            "WHERE (:name IS NULL OR C.name = :name) AND " +
            "(:age IS NULL OR C.AGE = :age) AND " +
            "(:idMovie IS NULL OR CMM.id_movie = :idMovie)", nativeQuery = true)

    List<CharacterEntity> findCharacterByParams(@Param("name") String name,
                                             @Param("age") String age,
                                             @Param("idMovie") Long idMovie);
}
