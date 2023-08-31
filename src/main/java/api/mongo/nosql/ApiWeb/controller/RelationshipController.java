package api.mongo.nosql.ApiWeb.controller;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.Movie.RelationshipDTO;
import api.mongo.nosql.Domain.service.RelationshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RelationshipController {

    private final RelationshipService service;

    @GetMapping("/relationships")
    public ResponseEntity<?> getAllMovies(){
        List<RelationshipDTO> relationships = service.findAll();
        return new ResponseEntity<>(relationships, relationships.size() > 0 ? OK : NOT_FOUND);
    }

    @PostMapping("/relationships")
    public ResponseEntity<?> createRelationShip(@RequestBody RelationshipDTO dto) {
        try {
            service.createRelationship(dto);
            return new ResponseEntity<RelationshipDTO>(dto, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/relationships/{id}")
    public ResponseEntity<?> getSingleMovieRelationship(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(service.findSingleMovieRelationship(id), OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }

    @PutMapping("/relationships")
    public ResponseEntity<?> updateById(@RequestBody RelationshipDTO dto) {
        try {
            service.updateSimilarity(dto);
            String ids = dto.getMovieId1() + " " + dto.getMovieId2();
            return new ResponseEntity<>("Update relationships with id "+ ids, OK);
        } catch (ConstraintViolationException e) {
            return new ResponseEntity<>(e.getMessage(), UNPROCESSABLE_ENTITY);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);

        }
    }
    @DeleteMapping("/relationships")
    public ResponseEntity<?> deleteById(HttpServletRequest request) {
        String id1 = request.getParameter("id1");
        String id2 = request.getParameter("id2");
        try {
            service.delete(id1,id2);
            return new ResponseEntity<>("Successfully deleted with id " + id1+" "+id2, OK);
        } catch (CollectionException e) {
            return new ResponseEntity<>(e.getMessage(), NOT_FOUND);
        }
    }
}
