package com.quizapp.core.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Question  {

    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<AnswerOption> answerOptions;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;

    @Column
    @Nationalized
    protected String Text;

    @Column
    private int maxPoints;

    public Question(String s, int i) {
        setText(s);
        setMaxPoints(i);
        setAnswerOptions(new ArrayList<>());
    }

    public boolean containsAnswer(AnswerOption answer) {
        return answerOptions.stream().anyMatch(a -> a.getText().equals(answer.getText()));
    }

    public List<AnswerOption> getRightAnswers() {
        return answerOptions.stream()
                .filter(a -> a.getPointsIfCorrect() != 0)
                .toList();
    }

    public void addAnswerToPoll(AnswerOption newAnswer) {
        if (newAnswer.getPointsIfCorrect() > maxPoints)
            throw new IllegalArgumentException("Answer cannot give more points than whole question");
        if (containsAnswer(newAnswer))
            throw new IllegalArgumentException("Cannot add same answer twice.");
        answerOptions.add(newAnswer);
        newAnswer.setQuestion(this);
    }

    public double getMaxPoints() {
        var wrapper = new Object() {
            int points = 0;
        };
        getRightAnswers().forEach(a -> wrapper.points += a.getPointsIfCorrect());
        return wrapper.points;
    }

}
