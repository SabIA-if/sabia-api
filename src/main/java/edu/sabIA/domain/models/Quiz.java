package edu.sabIA.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "quizzes")
@Getter
@Setter
public class Quiz {
    @Id
    private UUID id;
    private String theme;
    private int questionsQuantity;
    private String level;
    @Column(name = "docent_id")
    private User docent;
    @Column(name = "class_id")
    private UUID classId;
    private LocalDateTime createdAt;

    public Quiz() {
    }

    public Quiz(String theme) {
        this.id = UUID.randomUUID();
        this.theme = theme;
        this.createdAt = LocalDateTime.now();
    }

}
