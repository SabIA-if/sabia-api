package edu.sabIA.data.contracts.request.classroom;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;
import java.util.UUID;

public record CreateClassroomRequest(@NotEmpty String name, @NotEmpty String level, @NotEmpty UUID docentId, List<UUID> studentsIds) {
}
