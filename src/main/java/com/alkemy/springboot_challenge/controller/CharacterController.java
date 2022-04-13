package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.repositories.CharacterRepository;
import com.alkemy.springboot_challenge.entities.CharacterEntity;
import com.alkemy.springboot_challenge.http.CharacterNotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CharacterController {

    private final CharacterRepository repository;

    CharacterController(CharacterRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/characters/{id}")
    public CharacterEntity readOneCharacter (@PathVariable Long id){

        return repository.findById(id).
                orElseThrow(() -> new CharacterNotFoundException(id));
    }

    @PostMapping("/characters")
    public CharacterEntity createCharacter(@RequestBody CharacterEntity characterEntityRecieved){

        return repository.save(characterEntityRecieved);
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping("/characters/{id}")
    public CharacterEntity updateCharacter(@RequestBody CharacterEntity characterEntityRecieved, @PathVariable Long id){
        return repository.findById(id)
                .map(character -> {
                    character.setName(characterEntityRecieved.getName());
                    character.setImg(characterEntityRecieved.getImg());
                    character.setAge(characterEntityRecieved.getAge());
                    character.setStory(characterEntityRecieved.getStory());
                    character.setWeight(characterEntityRecieved.getWeight());
                    return repository.save(character);
                })
                .orElseGet(() -> {
                    characterEntityRecieved.setId(id);
                    return repository.save(characterEntityRecieved);
                });
    }

    @GetMapping("/characters")
    public List<CharacterEntity> readCharactersWithParams(@Param("name") String name,
                                                       @Param("age") String age,
                                                       @Param("movies") Long idMovies){
        return repository.findCharacterByParams(name, age, idMovies);
    }


    /*@GetMapping("characters")
    public List<CharacterEntity> readAllCharacters(){
        return repository.findAllCharacters();
    }*/




}
