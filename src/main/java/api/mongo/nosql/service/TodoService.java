package api.mongo.nosql.service;

import api.mongo.nosql.exception.TodoCollectionException;
import api.mongo.nosql.model.TodoDTO;

import java.util.List;

public interface TodoService {
    void createTodo(TodoDTO todo) throws TodoCollectionException;

    List<TodoDTO> getALlTodos();

    TodoDTO getSingleTodo(String id) throws TodoCollectionException;

    void updateTodo(String id, TodoDTO todo) throws TodoCollectionException;

    void deleteTodoByID(String id) throws TodoCollectionException;
}
