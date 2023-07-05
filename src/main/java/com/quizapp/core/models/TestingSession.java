package com.quizapp.core.models;

import com.quizapp.core.models.user.AppUser;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Builder
public class TestingSession {
    @Id
    @GeneratedValue
    protected int id;

    @Column
    private Date initDate;

    @Column
    private Date endDate;

    @Column
    private long timeAvailable;

    @Column
    private boolean isTerminated;

    @Column
    private double grade;


    public void setGrade(double grade){
        if (isEnded() || isTerminated)
            this.grade = grade;
        else
            throw new IllegalArgumentException("Cannot grade test before ending.");
    }

    public boolean isEnded(){
        return Date.from(Instant.ofEpochSecond(initDate.getTime() + timeAvailable))
                .before(Date.from(Instant.now()));
    }


    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
