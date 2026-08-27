package edu.sabIA.data.contracts.response.quiz;

import java.util.UUID;

public record QuizResponse(
    UUID id, 
    String theme,
    String[] topics,
    int numberOfQuestions, 
    Object quizJson, 
    int currentQuestion, 
    int score, 
    UUID userId, 
    boolean isFinished
) {}
