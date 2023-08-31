package api.mongo.nosql.Domain.mongoRepository;

import api.mongo.nosql.Domain.model.Movie.MovieDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends MongoRepository<MovieDTO,String> {

    @Query("{'title': ?0}")
    Optional<MovieDTO> findByTitle(String title);

}
