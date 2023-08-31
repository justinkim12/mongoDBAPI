package api.mongo.nosql.Domain.neo4jRepository;

import api.mongo.nosql.Domain.model.User.UserDTO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<UserDTO,Long> {

    Optional<UserDTO> findByName(String name);
}
