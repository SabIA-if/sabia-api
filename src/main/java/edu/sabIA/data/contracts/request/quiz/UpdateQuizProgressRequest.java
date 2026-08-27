package edu.sabIA.data.contracts.request.quiz;

import java.util.UUID;

public record UpdateQuizProgressRequest(UUID id, boolean hasScored) {
    
}
