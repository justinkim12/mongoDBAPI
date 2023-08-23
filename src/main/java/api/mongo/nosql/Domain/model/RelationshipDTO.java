package api.mongo.nosql.Domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Relationship")
public class RelationshipDTO {

    @Id
    private String rID; // Relationship PK

    private String movieId1;
    private String movieId2;
    private Double similarity;

    public RelationshipDTO(String movieId1, String movieId2, Double similarity) {
        this.movieId1 = movieId1;
        this.movieId2 = movieId2;
        this.similarity = similarity;
    }
}
