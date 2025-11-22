package edu.sabIA.controller;

import edu.sabIA.domain.dto.request.CreateUserRequest;
import edu.sabIA.domain.dto.request.GetUserRequest;
import edu.sabIA.domain.dto.request.UpdateUserRequest;
import edu.sabIA.domain.dto.response.CreateUserResponse;
import edu.sabIA.domain.dto.response.GetUserResponse;
import edu.sabIA.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> register(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = service.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable String id) {
        GetUserResponse response = service.getUser(new GetUserRequest(id));

        if (response == null) { // service retorna null se não encontrar
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GetUserResponse>> listUsers() {
        List<GetUserResponse> responses = service.listUsers();

        if (responses.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        boolean deleted = service.deleteUser(new GetUserRequest(id));

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok("Usuário deletado com sucesso");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(
            @PathVariable String id,
            @RequestBody UpdateUserRequest request
    ) {
        boolean updated = service.updateUser(
                new UpdateUserRequest(id, request.name(), request.email(), request.password())
        );

        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok("Atualização feita com sucesso");
    }
}
