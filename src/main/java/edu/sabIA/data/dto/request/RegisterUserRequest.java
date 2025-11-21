package edu.sabIA.data.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record RegisterUserRequest(
        @NotEmpty(message = "obrigatório") String username,
        @NotEmpty(message = "obrigatório") String name,
        @NotEmpty(message = "obrigatório") String email,
        @NotEmpty(message = "obrigatório") String password
) {}
