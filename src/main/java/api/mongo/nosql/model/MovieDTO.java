package api.mongo.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class MovieDTO {

    @Id
    private String movieId;

    private String title;
    private String genres;
    private Integer year;

    private Map<String, Object> meta;

    private Date createdAt;
    private Date updatedAt;

}
