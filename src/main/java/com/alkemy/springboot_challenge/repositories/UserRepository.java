package com.alkemy.springboot_challenge.repositories;

import com.alkemy.springboot_challenge.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    @Query(value = "SELECT * FROM USER WHERE USERNAME = :username", nativeQuery = true)
    Optional<UserEntity> findByUsername(@Param("username") String username);
}
