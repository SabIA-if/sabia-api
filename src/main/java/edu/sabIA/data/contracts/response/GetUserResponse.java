package edu.sabIA.data.contracts.response;

import java.util.UUID;

public record GetUserResponse(UUID id, String name, String username, String email) {
}

