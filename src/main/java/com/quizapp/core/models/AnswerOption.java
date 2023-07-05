package com.quizapp.core.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Getter
@Setter
@Entity(name = "answer_option")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerOption {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @OneToMany(mappedBy = "answerOption")
    private List<Answer> answers;

    @Column
    @Nationalized
    private String text;

    @Column
    private double pointsIfCorrect;
}

