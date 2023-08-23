package api.mongo.nosql.Domain.service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.MovieDTO;
import api.mongo.nosql.Domain.repository.MovieRepository;
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

    public void createMovie(MovieDTO movie) throws ConstraintViolationException, CollectionException {
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

    public MovieDTO getSingleMovie(String id) throws CollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isEmpty()) {
            throw new CollectionException(CollectionException.NotFoundException(id));
        } else {
            return optionalMovie.get();
        }
    }

    public void updateMovie(String id, MovieDTO movie) throws CollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isPresent()) {
            MovieDTO movieUpdate = optionalMovie.get();

            movieUpdate.setTitle(movie.getTitle());
            movieUpdate.setYear(movie.getYear());
            movieUpdate.setMeta(movie.getMeta());
            
            movieUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            repository.save(movieUpdate);
        } else {
            throw new CollectionException(CollectionException.NotFoundException(id));
        }
    }

    public void deleteMovieByID(String id) throws CollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new CollectionException(CollectionException.NotFoundException(id));
        }
    }
}
