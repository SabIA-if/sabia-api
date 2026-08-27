package edu.sabIA.rest.controller;

import edu.sabIA.data.contracts.request.classroom.CreateClassroomRequest;
import edu.sabIA.data.contracts.response.classroom.CreateClassroomResponse;
import edu.sabIA.data.mappers.ClassroomMappers;
import edu.sabIA.domain.models.Classroom;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.sabIA.data.service.ClassroomService;

@RestController
@RequestMapping("classrooms")
public class ClassroomController {
    private final ClassroomService service;
    private final ClassroomMappers mappers;

    public ClassroomController(ClassroomService service, ClassroomMappers mappers) {
        this.service = service;
        this.mappers = mappers;
    }

    @PostMapping("/new")
    public ResponseEntity<CreateClassroomResponse> createClassroom(@RequestBody CreateClassroomRequest request){
        try{
            Classroom entity = service.createClassroom(
                    request.docentId(),
                    request.studentsIds(),
                    request.name(),
                    request.level()
            );

            return ResponseEntity.ok(mappers.toCreateClassroomResponse(entity));
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
