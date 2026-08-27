package edu.sabIA.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.sabIA.data.contracts.request.CreateUserRequest;
import edu.sabIA.data.contracts.request.GetUserRequest;
import edu.sabIA.data.contracts.request.LoginRequest;
import edu.sabIA.data.contracts.request.UpdateUserRequest;
import edu.sabIA.data.contracts.response.CreateUserResponse;
import edu.sabIA.data.contracts.response.GetUserResponse;
import edu.sabIA.domain.models.User;
import edu.sabIA.infra.repository.UserRepository;

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

        return new CreateUserResponse(newUser.getId(), newUser.getEmail(), newUser.getUsername());
    }

    public User loginUser(LoginRequest request) {

        User entity = repository.findByUsername(request.username())
            .orElseThrow(() -> new RuntimeException("Usuário não existe"));

        if (!entity.getPasswordHash().equals(request.password())) {
            throw new RuntimeException("Senha incorreta");
        }

        return entity; 
    }

    public GetUserResponse getUser(GetUserRequest request) {
        Optional<User> consult = repository.findById(UUID.fromString(request.id()));

        if (consult.isEmpty()) {
            return null;
        }

        User entity = consult.get();

        return new GetUserResponse(
                entity.getId(),
                entity.getName(),
                entity.getUsername(),
                entity.getEmail()
        );
    }

    public List<GetUserResponse> listUsers() {
        List<User> users = repository.findAll();

        return users.stream()
                .map(user -> new GetUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail()
                ))
                .toList();
    }

    public boolean deleteUser(GetUserRequest request) {
        Optional<User> consult = repository.findById(UUID.fromString(request.id()));

        if (consult.isEmpty()) return false;

        repository.deleteById(UUID.fromString(request.id()));
        return true;
    }

    public boolean updateUser(String id, UpdateUserRequest request) {
        Optional<User> consult = repository.findById(UUID.fromString(id));

        if (consult.isEmpty()) return false;

        User entity = consult.get();
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPasswordHash(request.password());

        repository.save(entity);
        return true;
    }
}
