package api.mongo.nosql.Domain.service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.Movie.MovieDTO;
import api.mongo.nosql.Domain.mongoRepository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.*;

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

    @SuppressWarnings("unchecked")
    public void updateMovieDetails(String id, Object input) throws CollectionException{
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isEmpty())
            throw new CollectionException(CollectionException.NotFoundException(id));

        MovieDTO movieDTO = optionalMovie.get();
        if (input instanceof ArrayList)
            movieDTO.setRelated_movie((ArrayList<String>) input);
        else if(input instanceof Map)
            movieDTO.setMeta((Map<String, Object>) input);
        repository.save(movieDTO);
    }

    public void deleteMovieByID(String id) throws CollectionException {
        Optional<MovieDTO> optionalMovie = repository.findById(id);
        if (optionalMovie.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new CollectionException(CollectionException.NotFoundException(id));
        }
    }

    public List<MovieDTO> searchByTitle(String title) {
        List<MovieDTO> movieDTOList = repository.findByTitleLikeIgnoreCase(title);
        return movieDTOList;
    }
}
