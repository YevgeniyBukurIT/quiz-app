package com.quizapp.core.models;


import com.quizapp.core.models.user.AppUser;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Answer {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private AnswerOption answerOption;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Question question;
}
