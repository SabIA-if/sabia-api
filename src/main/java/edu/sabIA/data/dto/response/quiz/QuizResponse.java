package edu.sabIA.data.dto.response.quiz;

import java.util.UUID;

public record QuizResponse(
    UUID id, 
    String theme,
    String[] topics,
    int numberOfQuestions, 
    String quizJson, 
    int currentQuestion, 
    int score, 
    UUID userId, 
    boolean isFinished
) {}
