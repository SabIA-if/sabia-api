package edu.sabIA.data.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "Username é obrigatório") String username,
                           @NotEmpty(message = "Senha é obrigatória") String password) {

}
