package api.mongo.nosql.Domain.model.User;

import lombok.*;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Node("User")
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @Id
    private Long id;
    private String name;
    private List<String> watchedMovie = new ArrayList<String>();
    private List<String> recommendedMovie = new ArrayList<String>();

    @Relationship(type = "SIMILAR_TO", direction = Relationship.Direction.OUTGOING)
    private List<UserRelationship> similarUsers = new ArrayList<UserRelationship>();

    public UserDTO(Long id, String name, List<UserRelationship> similarUsers) {
        this.id = id;
        this.name = name;
        this.similarUsers = similarUsers;
    }
}


