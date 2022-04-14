package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.entities.UserEntity;
import com.alkemy.springboot_challenge.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    // Método sólo para confirmar que el USErREPOSITORY anda bien y devuelve el pass y el rol de la DB.
    @GetMapping("/auth/getUser/{username}")
    public UserEntity getUser(@PathVariable String username){
        return  userRepository.findByUsername(username)
                                        .orElseThrow(()-> new UsernameNotFoundException("Not found username: " + username));
    }
}
