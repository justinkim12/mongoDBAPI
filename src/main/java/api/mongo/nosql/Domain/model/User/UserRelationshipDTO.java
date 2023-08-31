package api.mongo.nosql.Domain.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

//@RelationshipProperties
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRelationshipDTO {

    private Long fromUserId;
    private Long toUserId;
    private Float similarity;

}
