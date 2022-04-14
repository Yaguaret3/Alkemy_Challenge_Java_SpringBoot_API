package com.alkemy.springboot_challenge.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity @Table(name = "user")
public class UserEntity {

    @Id @Getter @Setter @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id")
    private Long id;
    @Getter @Setter @Column(name = "username")
    private String username;
    @Getter @Setter @Column(name = "password")
    private String password;
    @Getter @Setter @Column(name = "role")
    private String role;
}
