package edu.sabIA.data.contracts.response.quiz;

import java.util.UUID;

public record QuizBasicInformationResponse(
    UUID id,
    String theme,
    String[] topics,
    int currentQuestion,
    int numberOfQuestions,
    int score
) {}
