package edu.sabIA.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(@NotEmpty String username, @NotEmpty String name,
                                 @NotEmpty String email, String password){}