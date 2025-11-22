package edu.sabIA.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import edu.sabIA.data.dto.request.CreateUserRequest;
import edu.sabIA.data.dto.request.GetUserRequest;
import edu.sabIA.data.dto.request.LoginRequest;
import edu.sabIA.data.dto.request.UpdateUserRequest;
import edu.sabIA.data.dto.response.CreateUserResponse;
import edu.sabIA.data.dto.response.GetUserResponse;
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

        return new CreateUserResponse(newUser.getEmail(), newUser.getUsername());
    }

    public String loginUser(LoginRequest request) {
        Optional<User> consult = repository.findByUsername(request.username());
        if (consult.isEmpty()) {
            throw new RuntimeException("Não exiiste usuário com esse username");
        }

        User entity = consult.get();

        if (!entity.getPasswordHash().equals(request.password())) {
            throw new RuntimeException("Senha incorreta");
        }

        return "Logado com sucesso";

    }

    public GetUserResponse getUser(GetUserRequest request) {
        Optional<User> consult = repository.findById(UUID.fromString(request.id()));

        if (consult.isEmpty()) {
            return null;
        }

        User entity = consult.get();

        return new GetUserResponse(
                entity.getName(),
                entity.getUsername(),
                entity.getEmail()
        );
    }

    public List<GetUserResponse> listUsers() {
        List<User> users = repository.findAll();

        return users.stream()
                .map(user -> new GetUserResponse(
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

    public boolean updateUser(UpdateUserRequest request) {
        Optional<User> consult = repository.findById(UUID.fromString(request.id()));

        if (consult.isEmpty()) return false;

        User entity = consult.get();
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPasswordHash(request.password());

        repository.save(entity);
        return true;
    }
}
