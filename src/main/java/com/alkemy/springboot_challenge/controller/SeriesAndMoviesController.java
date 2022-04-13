package com.alkemy.springboot_challenge.controller;

import com.alkemy.springboot_challenge.entities.CharacterEntity;
import com.alkemy.springboot_challenge.entities.GenreEntity;
import com.alkemy.springboot_challenge.entities.SeriesAndMoviesEntity;
import com.alkemy.springboot_challenge.http.CharacterNotFoundException;
import com.alkemy.springboot_challenge.http.SeriesAndMoviesNotFoundException;
import com.alkemy.springboot_challenge.repositories.CharacterRepository;
import com.alkemy.springboot_challenge.repositories.GenreRepository;
import com.alkemy.springboot_challenge.repositories.SeriesAndMoviesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeriesAndMoviesController {

    private final SeriesAndMoviesRepository repository;
    private final CharacterRepository characterRepository;
    private final GenreRepository genreRepository;

    SeriesAndMoviesController(SeriesAndMoviesRepository repository, CharacterRepository characterRepository, GenreRepository genreRepository) {
        this.repository = repository;
        this.characterRepository = characterRepository;
        this.genreRepository = genreRepository;
    }

    @GetMapping("/movies/{id}")
    public SeriesAndMoviesEntity readOneMovie (@PathVariable Long id){

        return repository.findById(id)
                .orElseThrow(() -> new SeriesAndMoviesNotFoundException(id));
    }

    @PostMapping("/movies")
    public SeriesAndMoviesEntity createMovie(@RequestBody SeriesAndMoviesEntity movieRecieved){
        return repository.save(movieRecieved);
    }

    @PutMapping("/movies/{id}")
    public SeriesAndMoviesEntity updateMovie(@RequestBody SeriesAndMoviesEntity movieRecieved, @PathVariable Long id){
        return repository.findById(id)
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
    }

    @DeleteMapping("/movies/{id}")
    public void deleteMovie(@PathVariable Long id){
        repository.deleteById(id);
    }

    @GetMapping("/movies")
    public List<SeriesAndMoviesEntity> readByParams(@RequestParam(required = false, name = "name") String title,
                                                    @RequestParam(required = false, name = "genre") String genre,
                                                    @RequestParam(required = false, name = "order") String order){

        return repository.findByParams(title, genre);
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
