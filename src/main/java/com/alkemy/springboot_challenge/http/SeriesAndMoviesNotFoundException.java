package com.alkemy.springboot_challenge.http;

public class SeriesAndMoviesNotFoundException extends RuntimeException {
    public SeriesAndMoviesNotFoundException (Long id){
        super("Could not found series or movie with ID: " + id);
    }
}
