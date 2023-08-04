package api.mongo.nosql.service;

import api.mongo.nosql.exception.MovieCollectionException;
import api.mongo.nosql.exception.MovieCollectionException;
import api.mongo.nosql.model.MovieDTO;
import api.mongo.nosql.model.MovieDTO;
import api.mongo.nosql.repository.MovieRepository;
import api.mongo.nosql.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;

    public void createMovie(MovieDTO movie) throws ConstraintViolationException, MovieCollectionException {
        movie.setCreatedAt(new Date(System.currentTimeMillis()));
        repository.save(movie);
    }

    public List<MovieDTO> getALlMovies() {
        List<MovieDTO> movies = repository.findAll();
        if (movies.size() > 0) {
            return movies;
        } else {
            return new ArrayList<MovieDTO>();
        }
    }

    public MovieDTO getSingleMovie(String id) throws MovieCollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isEmpty()) {
            throw new MovieCollectionException(MovieCollectionException.NotFoundException(id));
        } else {
            return optionalMovie.get();
        }
    }

    public void updateMovie(String id, MovieDTO movie) throws MovieCollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isPresent()) {
            MovieDTO movieUpdate = optionalMovie.get();

            movieUpdate.setTitle(movie.getTitle());
            movieUpdate.setYear(movie.getYear());
            movieUpdate.setMeta(movie.getMeta());
            
            movieUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            repository.save(movieUpdate);
        } else {
            throw new MovieCollectionException(MovieCollectionException.NotFoundException(id));
        }
    }

    public void deleteMovieByID(String id) throws MovieCollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new MovieCollectionException(MovieCollectionException.NotFoundException(id));
        }
    }
}
