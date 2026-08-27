package edu.sabIA.data.contracts.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateQuizRequest(@NotEmpty String theme, @NotEmpty int numberOfQuestions, @NotEmpty String userId){}
