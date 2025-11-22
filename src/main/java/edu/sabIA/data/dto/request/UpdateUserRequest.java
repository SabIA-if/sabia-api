package edu.sabIA.domain.dto.request;

public record UpdateUserRequest(String id, String name, String email, String password) {
}
