package edu.sabIA.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.sabIA.domain.models.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {

    Optional<Quiz> findById(UUID id);
    Optional<List<Quiz>> findByUserId(UUID userId);
}
