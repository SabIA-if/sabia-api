package edu.sabIA.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "quizzes")
public class Quiz {
    @Id
    private UUID id;
    private String theme;
    private String[] topics;
    private int numberOfQuestions;
    private String quizJson;
    private int currentQuestion;
    private int score;
    @Column(name = "user_id")
    private UUID userId;
    private LocalDateTime createdAt;
    private boolean isFinished;

    public Quiz(String theme, int numberOfQuestions, String quizJson, int score, UUID userId) {
        this.id = UUID.randomUUID();
        this.theme = theme;
        this.numberOfQuestions = numberOfQuestions;
        this.quizJson = quizJson;
        this.currentQuestion = 0;
        this.score = 0;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.isFinished = false;
    }

    public Quiz(String theme, int numberOfQuestions, String[] topics, String quizJson, int score, UUID userId) {
        this.id = UUID.randomUUID();
        this.theme = theme;
        this.numberOfQuestions = numberOfQuestions;
        this.quizJson = quizJson;
        this.topics = topics;
        this.currentQuestion = 0;
        this.score = 0;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.isFinished = false;
    }


}
