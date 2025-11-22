package edu.sabIA.data.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateUserRequest(@NotEmpty String username, @NotEmpty String name,
                                 @NotEmpty String email, String password){}