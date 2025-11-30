package edu.sabIA.data.dto.response.quiz;

import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

public record QuizResponse(
    UUID id, 
    String theme,
    String[] topics,
    int numberOfQuestions, 
    JsonNode quizJson, 
    int currentQuestion, 
    int score, 
    UUID userId, 
    boolean isFinished
) {}
