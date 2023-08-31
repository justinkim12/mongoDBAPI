package api.mongo.nosql.ApiWeb.controller;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.User.UserDTO;
import api.mongo.nosql.Domain.model.User.UserRelationshipDTO;
import api.mongo.nosql.Domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService service;

    @GetMapping("/users")
    public ResponseEntity<?> getAllMovies(){
        List<UserDTO> movies = service.findAll();
        return new ResponseEntity<>(movies, movies.size() > 0 ? OK : NOT_FOUND);
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            service.saveUser(userDTO);
            return new ResponseEntity<UserDTO>(userDTO, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(),CONFLICT);
        }
    }


    @PostMapping("/users/relationships")
    public ResponseEntity<?> createRelationship(@RequestBody UserRelationshipDTO userRelationshipDTO) {
        try {
            service.saveRelationship(userRelationshipDTO);
            return new ResponseEntity<UserRelationshipDTO>(userRelationshipDTO, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(),CONFLICT);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        try {
            return new ResponseEntity<>(service.findById(id), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

}
