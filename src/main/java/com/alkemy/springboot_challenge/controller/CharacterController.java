package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.dto.CharacterDTODetails;
import com.alkemy.springboot_challenge.dto.CharacterDTOGeneral;
import com.alkemy.springboot_challenge.repositories.CharacterRepository;
import com.alkemy.springboot_challenge.entities.CharacterEntity;
import com.alkemy.springboot_challenge.http.CharacterNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CharacterController {

    @Autowired
    private CharacterRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/characters/{id}")
    public CharacterDTODetails readOneCharacter (@PathVariable Long id){

        CharacterEntity found = repository.findById(id).
                orElseThrow(() -> new CharacterNotFoundException(id));

        return modelMapper.map(found, CharacterDTODetails.class);
    }

    @PostMapping("/characters")
    public CharacterDTODetails createCharacter(@RequestBody CharacterEntity characterEntityRecieved){

        CharacterEntity saved = repository.save(characterEntityRecieved);
        return modelMapper.map(saved, CharacterDTODetails.class);
    }

    @DeleteMapping("/characters/{id}")
    public void deleteCharacter(@PathVariable Long id){
        repository.deleteById(id);
    }

    @PutMapping("/characters/{id}")
    public CharacterDTODetails updateCharacter(@RequestBody CharacterEntity characterEntityRecieved, @PathVariable Long id){
        CharacterEntity updated = repository.findById(id)
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
        return modelMapper.map(updated, CharacterDTODetails.class);
    }

    @GetMapping("/characters")
    public List<CharacterDTOGeneral> readCharactersWithParams(@Param("name") String name,
                                                              @Param("age") String age,
                                                              @Param("movies") Long idMovies){
        List<CharacterEntity> entities = repository.findCharacterByParams(name, age, idMovies);
        List<CharacterDTOGeneral> response = new ArrayList<>();
        for(CharacterEntity entity : entities){
            response.add(modelMapper.map(entity, CharacterDTOGeneral.class));
        }
        return response;
    }
}
