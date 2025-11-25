package edu.sabIA.data.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateQuizRequest(@NotEmpty String theme, @NotEmpty int numberOfQuestions, @NotEmpty String userId){}
