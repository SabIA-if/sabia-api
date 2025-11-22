package edu.sabIA.service;

import edu.sabIA.domain.dto.request.CreateUserRequest;
import edu.sabIA.domain.dto.request.GetUserRequest;
import edu.sabIA.domain.dto.request.UpdateUserRequest;
import edu.sabIA.domain.dto.response.CreateUserResponse;
import edu.sabIA.domain.dto.response.GetUserResponse;
import edu.sabIA.domain.models.User;
import edu.sabIA.infra.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        User newUser = new User(
                request.username(),
                request.name(),
                request.email(),
                request.password()
        );

        repository.save(newUser);

        CreateUserResponse response = new CreateUserResponse(newUser.getEmail(), newUser.getUsername());

        return response;
    }

    public ResponseEntity<GetUserResponse> getUser(GetUserRequest request){
        Optional<User> consult = repository.findById(UUID.fromString(request.id()));
        if(consult.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User entity = consult.get();
        GetUserResponse response = new GetUserResponse(
                entity.getName(),
                entity.getUsername(),
                entity.getEmail()
        );

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    public ResponseEntity<List<GetUserResponse>> listUsers() {

        List<User> consults = repository.findAll();

        if (consults.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<GetUserResponse> responses = consults.stream()
                .map(user -> new GetUserResponse(
                        user.getName(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }

    public ResponseEntity<String> deleteUser(GetUserRequest request){
        Optional<User> consult = repository.findById(UUID.fromString(request.id()));

        if(consult.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        repository.deleteById(UUID.fromString(request.id()));
        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    public ResponseEntity<String> updateUser(UpdateUserRequest request) {

        Optional<User> consult = repository.findById(UUID.fromString(request.id()));
        if (consult.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User entity = consult.get();
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPasswordHash(request.password());

        repository.save(entity);

        return ResponseEntity.ok("Atualização feita com sucesso");
    }

}

