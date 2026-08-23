package edu.sabIA.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "class_student")
public class ClassStudent {
    @Id
    private UUID id;
    @Column(name = "student_id")
    private User student;
    @Column(name = "class_id")
    private Class classEntity;

    public ClassStudent(){}
}
