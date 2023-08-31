package api.mongo.nosql.ApiWeb.controller;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.Movie.MovieDTO;
import api.mongo.nosql.Domain.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MovieController {

    private final MovieService service;

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies(){
        List<MovieDTO> movies = service.getALlMovies();
        return new ResponseEntity<>(movies, movies.size() > 0 ? OK : NOT_FOUND);
    }

    @PostMapping("/movies")
    public ResponseEntity<?> createTodo(@RequestBody MovieDTO movieDTO) {
        try {
            service.createMovie(movieDTO);
            return new ResponseEntity<MovieDTO>(movieDTO, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(), CONFLICT);
        }
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(service.getSingleMovie(id), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody MovieDTO movieDTO) {
        try {
            service.updateMovie(id, movieDTO);
            return new ResponseEntity<>("Update Todo with id " + id, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);

        }
    }

    @PutMapping("/movies/details/{id}")
    public ResponseEntity<?> updateById2(@PathVariable("id") String id, @RequestBody Object movieDTO) {
        try {
            service.updateMovieDetails(id, movieDTO);
            return new ResponseEntity<>("Update Todo with id " + id, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);

        }
    }


    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            service.deleteMovieByID(id);
            return new ResponseEntity<>("Successfully deleted with id" + id, OK);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }
}
