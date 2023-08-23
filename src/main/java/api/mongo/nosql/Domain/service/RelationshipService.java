package api.mongo.nosql.Domain.service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.RelationshipDTO;
import api.mongo.nosql.Domain.repository.RelationshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RelationshipService {
    private final RelationshipRepository repository;

    public void createRelationship(RelationshipDTO relationship)  throws ConstraintViolationException   {
        repository.save(relationship);
    }

    public RelationshipDTO findRelationshipByRId(String rId) throws CollectionException {
        Optional<RelationshipDTO> dtoOptional = repository.findById(rId);
        if(dtoOptional.isEmpty())
            throw new CollectionException(CollectionException.NotFoundException(rId));
        else
            return dtoOptional.get();
    }

    public RelationshipDTO findRelationshipByMovieIDs(String id1, String id2) throws CollectionException {
        Optional<RelationshipDTO> dtoOptional = repository.findByIds(id1, id2);
        if (dtoOptional.isEmpty())
            throw new CollectionException(CollectionException.RelationshipNotFoundException(id1, id2));
        else
            return dtoOptional.get();
    }

    public List<RelationshipDTO> findSingleMovieRelationship(String id) {
        List<RelationshipDTO> byMovieId1 = repository.findByMovieId1(id);
        List<RelationshipDTO> byMovieId2 = repository.findByMovieId2(id);
        byMovieId1.addAll(byMovieId2);
        if (byMovieId1.isEmpty())
            return null;

        return byMovieId1;
    }

    public List<RelationshipDTO> findAll(){
        return repository.findAll();
    }

    public void updateSimilarity(RelationshipDTO relationshipDTO) throws CollectionException {

        String movieId1 = relationshipDTO.getMovieId1();
        String movieId2 = relationshipDTO.getMovieId2();
        Optional<RelationshipDTO> optionalDTO1 = repository.findByIds(movieId1, movieId2);
        if (optionalDTO1.isEmpty()) {
            Optional<RelationshipDTO> optionalDTO2 = repository.findByIds(movieId2, movieId1);
            if (optionalDTO2.isPresent()) {
                RelationshipDTO updateDTO = optionalDTO2.get();
                updateDTO.setSimilarity(relationshipDTO.getSimilarity());
                repository.save(updateDTO);
            } else
                throw new CollectionException(CollectionException.RelationshipNotFoundException(movieId1,movieId2));
        } else {
            RelationshipDTO updateDTO = optionalDTO1.get();
            updateDTO.setSimilarity(relationshipDTO.getSimilarity());
            repository.save(updateDTO);
        }
    }
    public void delete(String id1, String id2) throws CollectionException {
        Optional<RelationshipDTO> dtoOptional = repository.findByIds(id1, id2);
        if (dtoOptional.isEmpty()) {
            throw new CollectionException(CollectionException.RelationshipNotFoundException(id1, id2));
        } else {
            String rid = dtoOptional.get().getRID();
            repository.deleteById(rid);
        }
    }
}
