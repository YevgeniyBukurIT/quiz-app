package com.quizapp.core.models.user;

import com.quizapp.core.models.Answer;
import com.quizapp.core.models.Test;
import com.quizapp.core.models.TestingSession;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="app_user")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue
    private int id;
    @Nationalized
    private String firstname;
    @Nationalized
    private String lastname;
    private String email;
    private String password;

    @OneToMany
    private List<Test> createdTests;


    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @OneToMany
    private List<Answer> answers;

    @OneToMany
    private List<TestingSession> testingSessions;

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
