package edu.sabIA.domain.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "question_options")
@Getter
@Setter
public class QuestionOption {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    private String text;
    private boolean isCorrect;

    public QuestionOption(){}
}
