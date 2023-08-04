package api.mongo.nosql.repository;

import api.mongo.nosql.model.MovieDTO;
import api.mongo.nosql.model.TodoDTO;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface MovieRepository extends MongoRepository<MovieDTO,String> {

    @Query("{'title': ?0}")
    Optional<MovieDTO> findByTitle(String title);
}
