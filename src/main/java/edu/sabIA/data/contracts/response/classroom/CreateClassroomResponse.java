package edu.sabIA.data.contracts.response.classroom;

import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateClassroomResponse(@NotEmpty UUID id, @NotEmpty String name, @NotEmpty LocalDateTime createdAt) {
}
