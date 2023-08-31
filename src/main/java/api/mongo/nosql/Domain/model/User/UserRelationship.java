package api.mongo.nosql.Domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRelationship {

    @Id @GeneratedValue
    private Long id;

    private Float similarityScore;

    @TargetNode
    private UserDTO user;

    public UserRelationship(Float similarityScore, UserDTO user) {
        this.similarityScore = similarityScore;
        this.user = user;
    }


}
