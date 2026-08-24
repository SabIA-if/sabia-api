package edu.sabIA.data.service;

import edu.sabIA.domain.models.Classroom;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import edu.sabIA.domain.models.User;
import edu.sabIA.infra.repository.ClassroomRepository;
import edu.sabIA.infra.repository.UserRepository;

public class ClassroomService {

    private final UserRepository userRepository;

    private final ClassroomRepository repository;

    public ClassroomService(UserRepository userRepository, ClassroomRepository repository) {
        this.userRepository = userRepository;
        this.repository = repository;
    }

    public Classroom createClass(UUID docentId, List<UUID> studentsIds, String name, String level) {

        Optional<User> docent = userRepository.findById(docentId);
        if (docent.isEmpty()) {
            throw new RuntimeException("Docente não encontrado");
        }

        Classroom entity = new Classroom(
                name,
                docent.get(),
                studentsIds.size());

        return repository.save(entity);
    }
}
