package edu.sabIA.data.mappers;

import edu.sabIA.data.contracts.response.classroom.CreateClassroomResponse;
import edu.sabIA.domain.models.Classroom;

public class ClassroomMappers {
    public CreateClassroomResponse toCreateClassroomResponse(Classroom entity){
        return new CreateClassroomResponse(entity.getId(), entity.getName(), entity.getCreatedAt());
    }
}
