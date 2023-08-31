package api.mongo.nosql.Domain.service;

import api.mongo.nosql.Domain.exception.CollectionException;
import api.mongo.nosql.Domain.model.User.UserDTO;
import api.mongo.nosql.Domain.model.User.UserRelationship;
import api.mongo.nosql.Domain.model.User.UserRelationshipDTO;
import api.mongo.nosql.Domain.neo4jRepository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public void saveUser(UserDTO user) throws ConstraintViolationException, CollectionException {
        repository.save(user);
    }

    //관계 저장
    public void saveRelationship(UserRelationshipDTO dto) throws CollectionException {
        Long toUserId = dto.getToUserId();
        Long fromUserId = dto.getFromUserId();
        Float similarity = dto.getSimilarity();


        Optional<UserDTO> optionalFromUser = repository.findById(fromUserId);
        Optional<UserDTO> optionalToUser = repository.findById(toUserId);

        if (optionalFromUser.isEmpty() || optionalToUser.isEmpty()) {
            throw new CollectionException(CollectionException
                    .NotFoundException(String.valueOf(optionalFromUser)+" "+String.valueOf(optionalToUser)));
        }
        UserDTO toUser = optionalToUser.get();
        UserDTO fromUser = optionalFromUser.get();
        UserRelationship userRelationship = new UserRelationship(similarity, toUser);

        fromUser.getSimilarUsers().add(userRelationship);
        repository.save(fromUser);
    }


    public List<UserDTO> findAll(){
        return repository.findAll();}

    public UserDTO findById(Long id) throws CollectionException {
        Optional<UserDTO> optionalUserDTO = repository.findById(id);
        if (optionalUserDTO.isEmpty() || optionalUserDTO.isEmpty()) {
            throw new CollectionException(CollectionException
                    .NotFoundException(String.valueOf(optionalUserDTO)));
        }
        return optionalUserDTO.get();
    }

}
