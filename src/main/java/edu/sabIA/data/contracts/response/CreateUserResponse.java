package edu.sabIA.data.contracts.response;

import java.util.UUID;
public record CreateUserResponse (UUID id, String email, String username){
}
