package api.mongo.nosql.controller;

import api.mongo.nosql.exception.MovieCollectionException;
import api.mongo.nosql.model.MovieDTO;
import api.mongo.nosql.repository.MovieRepository;
import api.mongo.nosql.service.MovieService;
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

    private final MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMovies(){
        List<MovieDTO> movies = movieService.getALlMovies();
        return new ResponseEntity<>(movies, movies.size() > 0 ? OK : NOT_FOUND);
    }

    @PostMapping("/movies")
    public ResponseEntity<?> createTodo(@RequestBody MovieDTO movieDTO) {
        try {
            movieService.createMovie(movieDTO);
            return new ResponseEntity<MovieDTO>(movieDTO, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (MovieCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), CONFLICT);
        }
    }

    @GetMapping("/movies/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(movieService.getSingleMovie(id), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/movies/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody MovieDTO movieDTO) {
        try {
            movieService.updateMovie(id, movieDTO);
            return new ResponseEntity<>("Update Todo with id " + id, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (MovieCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);

        }
    }
    @DeleteMapping("/movies/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            movieService.deleteMovieByID(id);
            return new ResponseEntity<>("Successfully deleted with id" + id, OK);
        } catch (MovieCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }
}
