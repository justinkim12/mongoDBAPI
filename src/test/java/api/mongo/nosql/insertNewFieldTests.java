package api.mongo.nosql;


import api.mongo.nosql.model.TodoDTO;
import api.mongo.nosql.repository.TodoRepository;
import api.mongo.nosql.service.TodoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class insertNewFieldTests {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;

    @AfterEach
    void Clear(){
        todoRepository.deleteAll();
    }
    @Test
    void saveObject(){
        String todo = "todo4";
        TodoDTO todoDTO = new TodoDTO(todo, "description1", true);
        Map<String, Object> meta = new HashMap<>();
        meta.put("someId", 12);
        meta.put("actors", Arrays.asList("A","B","C"));
        todoDTO.setMeta(meta);
        todoRepository.save(todoDTO);

        TodoDTO findTodo = todoRepository.findByTodo(todo).orElseThrow();
        Assertions.assertThat(findTodo.getMeta().get("someId")).isSameAs(12);

    }
}
