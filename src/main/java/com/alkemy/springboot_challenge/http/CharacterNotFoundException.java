package com.alkemy.springboot_challenge.http;

public class CharacterNotFoundException extends RuntimeException{

    public CharacterNotFoundException(Long id){
        super("Could not found "+id);
    }
}
