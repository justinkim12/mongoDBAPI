package api.mongo.nosql.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")//RDB의 entity같은 것
public class TodoDTO {

    @Id
    private String id;

    @NotNull(message = "todo cannot be null")
    private String todo;

    @NotNull(message = "description cannot be null")
    private String description;

    @NotNull(message = "completed cannot be null")
    private Boolean completed;


    private Map<String, Object> meta;

    private Date createdAt;
    private Date updatedAt;

    public TodoDTO(String todo, String description, Boolean completed) {
        this.todo = todo;
        this.description = description;
        this.completed = completed;
    }
}
