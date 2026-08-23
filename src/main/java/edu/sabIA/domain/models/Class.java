package edu.sabIA.domain.models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "classes")
@Getter
@Setter
public class Class {
    @Id
    private UUID id;
    @Column(name = "docent_id")
    private User docent;
    @Column(name = "students_quantity")
    private int studentsQuantity;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Class(){}
}
