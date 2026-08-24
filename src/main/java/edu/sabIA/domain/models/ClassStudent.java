package edu.sabIA.domain.models;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private Classroom classroom;

    public ClassStudent() {
    }
}
