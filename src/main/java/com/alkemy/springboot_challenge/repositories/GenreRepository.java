package com.alkemy.springboot_challenge.repositories;

import com.alkemy.springboot_challenge.entities.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
}
