package api.mongo.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")//RDB의 entity같은 것
public class TodoDTO {

    @Id
    private String id;
    private String todo;
    private String description;
    private Boolean completed;
    private Date createdAt;
    private Date updatedAt;

}
