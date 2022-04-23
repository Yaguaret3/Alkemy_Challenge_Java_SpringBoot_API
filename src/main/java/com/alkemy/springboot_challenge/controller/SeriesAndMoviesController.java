package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.dto.SeriesAndMoviesDTODetails;
import com.alkemy.springboot_challenge.dto.SeriesAndMoviesDTOGeneral;
import com.alkemy.springboot_challenge.entities.CharacterEntity;
import com.alkemy.springboot_challenge.entities.GenreEntity;
import com.alkemy.springboot_challenge.entities.SeriesAndMoviesEntity;
import com.alkemy.springboot_challenge.http.CharacterNotFoundException;
import com.alkemy.springboot_challenge.http.SeriesAndMoviesNotFoundException;
import com.alkemy.springboot_challenge.repositories.CharacterRepository;
import com.alkemy.springboot_challenge.repositories.GenreRepository;
import com.alkemy.springboot_challenge.repositories.SeriesAndMoviesRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SeriesAndMoviesController {

    @Autowired
    private SeriesAndMoviesRepository repository;
    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/movies/{id}")
    public SeriesAndMoviesDTODetails readOneMovie (@PathVariable Long id){

        SeriesAndMoviesEntity found = repository.findById(id)
                .orElseThrow(() -> new SeriesAndMoviesNotFoundException(id));
        return modelMapper.map(found, SeriesAndMoviesDTODetails.class);
    }

    @PostMapping("/movies")
    public SeriesAndMoviesDTODetails createMovie(@RequestBody SeriesAndMoviesEntity movieRecieved){
        SeriesAndMoviesEntity saved = repository.save(movieRecieved);
        return modelMapper.map(saved, SeriesAndMoviesDTODetails.class);
    }

    @PutMapping("/movies/{id}")
    public SeriesAndMoviesDTODetails updateMovie(@RequestBody SeriesAndMoviesEntity movieRecieved, @PathVariable Long id){
        SeriesAndMoviesEntity updated = repository.findById(id)
                .map(seriesOrMovie -> {
                    seriesOrMovie.setImg(movieRecieved.getImg());
                    seriesOrMovie.setGenre(movieRecieved.getGenre());
                    seriesOrMovie.setRating(movieRecieved.getRating());
                    seriesOrMovie.setTitle(movieRecieved.getTitle());
                    seriesOrMovie.setPremiereDate(movieRecieved.getPremiereDate());
                    return repository.save(seriesOrMovie);
                })
                .orElseGet(() -> {
                    movieRecieved.setId(id);
                    return repository.save(movieRecieved);
                });
        return modelMapper.map(updated, SeriesAndMoviesDTODetails.class);
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable Long id){
        repository.deleteById(id);
    }

    @GetMapping("/movies")
    public List<SeriesAndMoviesDTOGeneral> readByParams(@RequestParam(required = false, name = "name") String title,
                                                        @RequestParam(required = false, name = "genre") String genre,
                                                        @RequestParam(required = false, name = "order") String order){

        List<SeriesAndMoviesEntity> entities = repository.findByParams(title, genre);
        List<SeriesAndMoviesDTOGeneral> response = new ArrayList<>();
        for(SeriesAndMoviesEntity entity : entities){
            response.add(modelMapper.map(entity, SeriesAndMoviesDTOGeneral.class));
        }
        return response;
    }

    @PostMapping("/movies/{idMovie}/characters/{idCharacter}")
    public void addCharacterToSeriesOrMovie(@PathVariable("idMovie") Long idMovie,
                                            @PathVariable("idCharacter") Long idCharacter){
        SeriesAndMoviesEntity movie = repository.findById(idMovie).orElseThrow(() -> new SeriesAndMoviesNotFoundException(idMovie));
        CharacterEntity character = characterRepository.findById(idCharacter).orElseThrow(() -> new CharacterNotFoundException(idCharacter));

        movie.getCharacters().add(character);
        character.getSeriesAndMovies().add(movie);

        repository.save(movie);
        characterRepository.save(character);
    }
    @DeleteMapping("/movies/{idMovie}/characters/{idCharacter}")
    public void deleteCharacterFromSeriesOrMovie(@PathVariable("idMovie") Long idMovie,
                                            @PathVariable("idCharacter") Long idCharacter){

        SeriesAndMoviesEntity movie = repository.findById(idMovie).orElseThrow(() -> new SeriesAndMoviesNotFoundException(idMovie));
        CharacterEntity character = characterRepository.findById(idCharacter).orElseThrow(() -> new CharacterNotFoundException(idCharacter));

        movie.getCharacters().remove(character);
        character.getSeriesAndMovies().remove(movie);

        repository.save(movie);
        characterRepository.save(character);
    }

    // No solicitados en las consignas, pero útiles para la aplicación:
    @PostMapping("/movies/{idMovie}/genres/{idGenre}")
    public void addGenreToSeriesOrMovie(@PathVariable("idMovie") Long idMovie,
                                            @PathVariable("idGenre") Long idGenre){
        SeriesAndMoviesEntity movie = repository.findById(idMovie).orElseThrow(() -> new SeriesAndMoviesNotFoundException(idMovie));
        GenreEntity genre = genreRepository.findById(idGenre).orElseGet(() -> new GenreEntity());

        movie.getGenre().add(genre);
        genre.getSeriesAndMovies().add(movie);

        repository.save(movie);
        genreRepository.save(genre);
    }
    @DeleteMapping("/movies/{idMovie}/genres/{idGenre}")
    public void deleteGenreFromSeriesOrMovie(@PathVariable("idMovie") Long idMovie,
                                                 @PathVariable("idGenre") Long idGenre){

        SeriesAndMoviesEntity movie = repository.findById(idMovie).orElseThrow(() -> new SeriesAndMoviesNotFoundException(idMovie));
        GenreEntity genre = genreRepository.findById(idGenre).orElseGet(() -> new GenreEntity());

        movie.getGenre().remove(genre);
        genre.getSeriesAndMovies().remove(movie);

        repository.save(movie);
        genreRepository.save(genre);
    }

}