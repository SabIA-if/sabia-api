package edu.sabIA.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sabIA.data.dto.request.CreateUserRequest;
import edu.sabIA.data.dto.request.GetUserRequest;
import edu.sabIA.data.dto.request.LoginRequest;
import edu.sabIA.data.dto.request.UpdateUserRequest;
import edu.sabIA.data.dto.response.CreateUserResponse;
import edu.sabIA.data.dto.response.GetUserResponse;
import edu.sabIA.data.service.UserService;
import edu.sabIA.domain.models.User;
import jakarta.servlet.http.HttpSession;

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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request, HttpSession session) {
        try {
            User user = service.loginUser(request); 
            session.setAttribute("usuarioLogado", user.getId());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserResponse> getUser(@PathVariable String id) {
        GetUserResponse response = service.getUser(new GetUserRequest(id));

        if (response == null) {
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
        boolean updated = service.updateUser(id, request);

        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok("Atualização feita com sucesso");
    }
}
