package edu.sabIA.domain.models;

import java.util.UUID;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "questions")
@Getter
@Setter
public class Question {
    @Id
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    private String statement;
    @Column(name = "was_approved")
    private boolean wasApproved;

    public Question(){}

}
