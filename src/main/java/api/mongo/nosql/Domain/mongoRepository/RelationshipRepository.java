package api.mongo.nosql.Domain.mongoRepository;

import api.mongo.nosql.Domain.model.Movie.RelationshipDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RelationshipRepository extends MongoRepository<RelationshipDTO,String> {

    List<RelationshipDTO> findByMovieId1(String id);
    List<RelationshipDTO> findByMovieId2(String id);

    @Query("{'movieId1' : ?0, 'movieId2': ?1}")
    Optional<RelationshipDTO> findByIds(String id1, String id2);
}
