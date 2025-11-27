package edu.sabIA.data.dto.request.quiz;

import java.util.UUID;

public record UpdateQuizProgressRequest(UUID id, boolean hasScored) {
    
}
