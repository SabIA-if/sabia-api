package edu.sabIA.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String[] topics;

    private int numberOfQuestions;

    @Column(columnDefinition = "TEXT")
    private String quizJson;

    private int currentQuestion;

    private int score;

    @Column(name = "user_id")
    private UUID userId;

    private boolean isFinished;

    private int questionsQuantity;
    private String level;
    @ManyToOne
    @JoinColumn(name = "docent_id")
    private User docent;
    @Column(name = "classroom_id")
    private UUID classroomId;
    private LocalDateTime createdAt;

    public Quiz() {
    }

    public Quiz(String theme) {
        this.id = UUID.randomUUID();
        this.theme = theme;
        this.createdAt = LocalDateTime.now();
    }

    public Quiz(String theme, int numberOfQuestions, String quizJson, int score, UUID userId) {
        this.id = UUID.randomUUID();
        this.theme = theme;
        this.numberOfQuestions = numberOfQuestions;
        this.questionsQuantity = numberOfQuestions;
        this.quizJson = quizJson;
        this.currentQuestion = 0;
        this.score = score;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.isFinished = false;
    }

}
