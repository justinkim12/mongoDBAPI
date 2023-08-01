package api.mongo.nosql.service;

import api.mongo.nosql.exception.TodoCollectionException;
import api.mongo.nosql.model.TodoDTO;
import api.mongo.nosql.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepository.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todo);
        }


    }

    @Override
    public List<TodoDTO> getALlTodos() {
        List<TodoDTO> todos = todoRepository.findAll();
        if (todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<TodoDTO>();
        }

    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        } else {
            return optionalTodo.get();
        }
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodoDTO = todoRepository.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepository.findByTodo(todo.getTodo());
        if (optionalTodoDTO.isPresent()) {

            //다른 ID에 같은 이름이 있는 경우
            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }

            TodoDTO todoUpdate = optionalTodoDTO.get();

            todoUpdate.setTodo(todo.getTodo());
            todoUpdate.setDescription(todo.getDescription());
            todoUpdate.setCompleted(todo.getCompleted());
            todoUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepository.save(todoUpdate);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));

        }
    }

    @Override
    public void deleteTodoByID(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            todoRepository.deleteById(id);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }
}
