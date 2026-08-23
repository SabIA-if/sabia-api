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
@Table(name = "quiz_pontuation")
@Getter
@Setter
public class QuizPontuation {
    @Id
    private UUID id;
    @Column(name = "quiz_id")
    private Quiz quiz;
    @Column(name = "student_id")
    private User student;
    private int pontuation;
    @Column(name = "finalized_at")
    private LocalDateTime finalizedAt;

    public QuizPontuation(){}
}
