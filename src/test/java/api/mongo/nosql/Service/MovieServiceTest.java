package api.mongo.nosql.Service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.MovieDTO;
import api.mongo.nosql.Domain.repository.MovieRepository;
import api.mongo.nosql.Domain.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieServiceTest {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MovieService service;

    @BeforeEach
    void insert() throws CollectionException {
        Date createdAt = new Date(System.currentTimeMillis());
        MovieDTO DTO1 = new MovieDTO("1", "AI", "SF", 1999,new ArrayList<>() ,new HashMap<>(), createdAt, null);
        MovieDTO DTO2 = new MovieDTO("2", "Toy Story", "Animation", 1997, new ArrayList<>(),new HashMap<>(), createdAt, null);
        MovieDTO DTO3 = new MovieDTO("3", "Avengers", "Action", 2008,new ArrayList<>(), new HashMap<>(), createdAt, null);

        service.createMovie(DTO1);
        service.createMovie(DTO2);
        service.createMovie(DTO3);
    }

    @Test()
    void save(){
        assertThat(repository.findAll().size()).isEqualTo(3);
    }

    @Test()
    void findFail(){
        String id = "4";
        assertThrows(CollectionException.class,()->{
            service.getSingleMovie(id);
        });
    }

    @Test
    void update(){
        String id = "1";
        try {
            MovieDTO dto = new MovieDTO();
            service.updateMovie("1", dto);

            MovieDTO movie = service.getSingleMovie("1");
            assertThat(movie.getTitle()).isEqualTo(null);
            assertNotNull(movie.getUpdatedAt());
        } catch (CollectionException e) {
        }
    }

    @Test
    void delete() throws CollectionException {
        String id = "1";
        service.deleteMovieByID(id);
        assertThat(repository.findAll().size()).isEqualTo(2);

    }

}