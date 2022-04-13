package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.entities.GenreEntity;
import com.alkemy.springboot_challenge.repositories.GenreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GenreController {

    private final GenreRepository repository;

    GenreController(GenreRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/genres")
    public GenreEntity createGenre(@RequestBody GenreEntity genreRecieved){

        return repository.save(genreRecieved);
    }
    @GetMapping("/genres")
    public List<GenreEntity> readAllGenres(){
        return repository.findAll();
    }
    @GetMapping("/genres/{id}")
    public GenreEntity readGenreById(@PathVariable Long id){
        return repository.findById(id).orElse(new GenreEntity());
    }
    @PutMapping("/genres/{id}")
    public GenreEntity updateGenre(@PathVariable Long id, @RequestBody GenreEntity genreRecieved){
        return repository.findById(id)
                .map( genreEntity -> {
                    genreEntity.setImg(genreRecieved.getImg());
                    genreEntity.setName(genreRecieved.getName());
                    return repository.save(genreEntity);
                }).orElseGet(() -> {
                    genreRecieved.setId(id);
                    return repository.save(genreRecieved);
                });
    }
    @DeleteMapping("/genres/{id}")
    public void deleteGenre(@PathVariable Long id){
        repository.deleteById(id);
    }


}
