package api.mongo.nosql.Service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.Movie.RelationshipDTO;
import api.mongo.nosql.Domain.mongoRepository.RelationshipRepository;
import api.mongo.nosql.Domain.service.RelationshipService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class RelationshipServiceTest {

    @Autowired
    private RelationshipService service;

    @Autowired
    private RelationshipRepository repository;

    @BeforeEach
    void insert(){
        RelationshipDTO dto1 = new RelationshipDTO("1","1","2",2.0F);
        RelationshipDTO dto2 = new RelationshipDTO("2","2","3",1.0F);
        RelationshipDTO dto3 = new RelationshipDTO("3","4","5",3.0F);

        service.createRelationship(dto1);
        service.createRelationship(dto2);
        service.createRelationship(dto3);
    }

    @Test
    void save(){
        List<RelationshipDTO> repositoryAll = repository.findAll();
        Assertions.assertThat(repositoryAll.size()).isEqualTo(3);
    }

    @Test
    void findOne(){
        String movieId = "2";
        List<RelationshipDTO> dtos = service.findSingleMovieRelationship(movieId);
        Assertions.assertThat(dtos.size()).isEqualTo(2);
    }

    @Test
    void update() throws CollectionException {
        String movieId1 = "1";
        String movieId2 = "2";
        Float similarity = 10.0F;
        RelationshipDTO dto = new RelationshipDTO(movieId1, movieId2, similarity);

        service.updateSimilarity(dto);

        Float result = service.findRelationshipByMovieIDs(movieId1, movieId2).getSimilarity();
        Assertions.assertThat(result).isEqualTo(similarity);

    }
}
