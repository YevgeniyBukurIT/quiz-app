package com.quizapp.core.models;


import com.quizapp.core.models.user.AppUser;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.context.annotation.Lazy;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public final static short TEST_MIN_DURATION_MINUTES = 5;

    @OneToMany(mappedBy = "test")
    private List<Answer> answers;

    private boolean isGraded;
    private boolean isForm;

    @Column
    @Nullable
    private LocalDateTime startDate;

    @Column
    @Nullable
    private LocalDateTime endDate;

    @Column
    private String authCode;

    @ManyToOne
    private AppUser creator;

    @Column
    @Nationalized
    private String theme;

    @Column
    @Nationalized
    private String name;

    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL)
    private List<Question> questions;

    public void addQuestion(Question question){
        if (getState() == TestState.FINISHED)
            throw new DateTimeException("Cant add new question");
        ensureQuestionNotExists(question);
        question.setTest(this);
        questions.add(question);
    }

    public double getMaxPoints(){
        return questions.stream()
                .map(Question::getMaxPoints)
                .reduce(Double::sum).get();

    }

    public long getDuration(){
        return startDate != null ? Duration.between(startDate,endDate).toMillis() : 1000000000;
    }


    public TestState getState(){
        var now = LocalDateTime.now();

        if (startDate == null)
            return TestState.ONGOING;

        if (startDate.isAfter(now))
            return TestState.WAITING;
        if (now.isAfter(startDate) && now.isBefore(endDate))
            return TestState.ONGOING;
        if (now.isAfter(endDate))
            return TestState.ONGOING;
        return null;
    }

    public void ensureQuestionNotExists(Question question){
        if (questions.stream().anyMatch(q -> q.getText().equals(question.getText())))
            throw new IllegalArgumentException("Such question already exist.");
    }

    public void setStartDate(LocalDateTime startDate) {
        if (!startDate.isAfter(LocalDateTime.now()))
            throw new DateTimeException("Illegal date and/or time");

        this.startDate = startDate;
    }

    public boolean isStarted(){
        return LocalDateTime.now().isAfter(getStartDate());
    }

    public void setEndDate(LocalDateTime endDate) {
        if (startDate == null)
            throw new DateTimeException("Cannot set end date without start date.");
        if (startDate.isAfter(endDate))
            throw new DateTimeException("Illegal date and/or time.");
        if (startDate.isAfter(endDate.minusMinutes(TEST_MIN_DURATION_MINUTES)))
            throw new DateTimeException("Test minimum duration is 5 minutes.");
        this.endDate = endDate;
    }
}
