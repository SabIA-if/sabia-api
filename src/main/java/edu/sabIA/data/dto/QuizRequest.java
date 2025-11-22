package edu.sabIA.domain.dto;

import lombok.Data;

@Data
public class QuizRequest {
    private String theme;
    private String numberOfQuestions;
}

