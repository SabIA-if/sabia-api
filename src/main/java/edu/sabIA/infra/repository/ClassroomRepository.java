package edu.sabIA.infra.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sabIA.domain.models.Classroom;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

}
