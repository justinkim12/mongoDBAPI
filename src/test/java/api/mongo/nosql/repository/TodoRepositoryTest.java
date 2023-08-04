package api.mongo.nosql.repository;

import api.mongo.nosql.model.TodoDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.MongoRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TodoRepositoryTest {

    @Autowired
    private MongoRepository mongoRepository;

    @AfterEach
    void Clear(){
        mongoRepository.deleteAll();
    }

    @Test()
    void save(){
        TodoDTO todoDTO1 = new TodoDTO("todo1", "description1", true);
        TodoDTO todoDTO2 = new TodoDTO("todo2", "description2", true);
        TodoDTO todoDTO3 = new TodoDTO("todo3", "description3", false);

        mongoRepository.save(todoDTO1);
        mongoRepository.save(todoDTO2);
        mongoRepository.save(todoDTO3);

        Assertions.assertThat(mongoRepository.findAll().size()).isEqualTo(3);
    }

}