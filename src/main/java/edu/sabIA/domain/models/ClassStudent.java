package edu.sabIA.domain.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "class_student")
@Getter
@Setter
public class ClassStudent {
    @Id
    private UUID id;
    @Column(name = "student_id")
    private User student;
    @Column(name = "class_id")
    private Classroom classroom;

    public ClassStudent() {
    }
}
