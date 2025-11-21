package edu.sabIA.infra.controllers;


import edu.sabIA.data.dto.request.LoginRequest;
import edu.sabIA.data.dto.request.RegisterUserRequest;
import edu.sabIA.data.dto.response.LoginuserResponse;
import edu.sabIA.data.dto.response.ResponseUserResponse;
import edu.sabIA.domain.models.User;
import edu.sabIA.infra.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<LoginuserResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return null;
    }

    public ResponseEntity<ResponseUserResponse> register(@Valid @RequestBody RegisterUserRequest request){
        User newUser = new User(request.username(), request.name(), request.email(), passwordEncoder.encode(request.password()));
        userRepository.save(newUser);

        return ResponseEntity.ok(new ResponseUserResponse(newUser.getName(), newUser.getEmail()));
    }
}
