package api.mongo.nosql.controller;

import api.mongo.nosql.model.TodoDTO;
import api.mongo.nosql.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos(){
        List<TodoDTO> todoDTOS = todoRepository.findAll();
        if (todoDTOS.size() > 0) {
            return new ResponseEntity<>(todoDTOS, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            todoDTO.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoDTO);
            return new ResponseEntity<TodoDTO>(todoDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        Optional<TodoDTO> todoDTOOptional = todoRepository.findById(id);
        if (todoDTOOptional.isPresent()) {
            return new ResponseEntity<>(todoDTOOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todoDTO) {
        Optional<TodoDTO> todoDTOOptional = todoRepository.findById(id);
        if (todoDTOOptional.isPresent()) {
            TodoDTO todoToSave = todoDTOOptional.get();
            todoToSave.setTodo(todoDTO.getTodo() != null ? todoDTO.getTodo() : todoToSave.getTodo());
            todoToSave.setCompleted(todoDTO.getCompleted() != null ? todoDTO.getCompleted() : todoToSave.getCompleted());
            todoToSave.setDescription(todoDTO.getDescription() != null ? todoDTO.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoToSave);
            return new ResponseEntity<>(todoToSave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Todo not found with id" + id, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        try {
            todoRepository.deleteById(id);
            return new ResponseEntity<>("Successfully deleted with id" + id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
