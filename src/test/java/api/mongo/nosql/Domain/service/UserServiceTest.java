package api.mongo.nosql.Domain.service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.User.UserDTO;
import api.mongo.nosql.Domain.model.User.UserRelationship;
import api.mongo.nosql.Domain.model.User.UserRelationshipDTO;
import api.mongo.nosql.Domain.neo4jRepository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @Autowired
    UserRepository repository;

    @Autowired
    UserService service;

    @AfterAll
    public void delete(){

        repository.deleteAll();
    }
    @Test
    public void testAddSimilarity() throws CollectionException {

        UserDTO user1 = new UserDTO(1L, "Amy", new ArrayList<UserRelationship>());
        UserDTO user2 = new UserDTO(2L, "Sam", new ArrayList<UserRelationship>());

        service.saveUser(user1);
        service.saveUser(user2);

        UserRelationshipDTO relationshipDTO = new UserRelationshipDTO(1L, 2L, 3.1F);
        service.saveRelationship(relationshipDTO);

        Assertions.assertThat(service.findById(1L).getSimilarUsers().size()).isEqualTo(1);
    }
}