package api.mongo.nosql.repository;

import api.mongo.nosql.model.MovieDTO;
import api.mongo.nosql.model.TodoDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.HashMap;

@SpringBootTest
class MovieRepositoryTest {

    @Autowired
    private MongoRepository mongoRepository;

    @AfterEach
    void Clear(){
        mongoRepository.deleteAll();
    }

    @Test()
    void save(){
//
        Date createdAt = new Date(System.currentTimeMillis());
        MovieDTO DTO1 = new MovieDTO("1", "AI", "SF", 1999, new HashMap<>(), createdAt, null);
        MovieDTO DTO2 = new MovieDTO("2", "Toy Story", "Animation", 1997, new HashMap<>(), createdAt, null);
        MovieDTO DTO3 = new MovieDTO("3", "Avengers", "Action", 2008, new HashMap<>(), createdAt, null);

        mongoRepository.save(DTO1);
        mongoRepository.save(DTO2);
        mongoRepository.save(DTO3);

        Assertions.assertThat(mongoRepository.findAll().size()).isEqualTo(3);
    }

}