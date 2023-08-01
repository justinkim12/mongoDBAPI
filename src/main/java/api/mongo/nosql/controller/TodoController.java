package api.mongo.nosql.controller;

import api.mongo.nosql.exception.TodoCollectionException;
import api.mongo.nosql.model.TodoDTO;
import api.mongo.nosql.repository.TodoRepository;
import api.mongo.nosql.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;
    private final TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todoDTOS = todoService.getALlTodos();
        return new ResponseEntity<>(todoDTOS, todoDTOS.size() > 0 ? OK : NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            todoService.createTodo(todoDTO);
            return new ResponseEntity<TodoDTO>(todoDTO, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), CONFLICT);
        }

    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(todoService.getSingleTodo(id), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todoDTO) {
        try {
            todoService.updateTodo(id, todoDTO);
            return new ResponseEntity<>("Update Todo with id " + id, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);

        }
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            todoService.deleteTodoByID(id);
            return new ResponseEntity<>("Successfully deleted with id" + id, OK);
        } catch (TodoCollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }
}
