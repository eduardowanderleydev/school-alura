package br.com.alura.school.enrollment;

import br.com.alura.school.course.Course;
import br.com.alura.school.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
public class Enrollment {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private User user;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private LocalDateTime date;

    @Deprecated
    protected Enrollment(){
    }

    public Enrollment(Course course, User user) {
        this.course = course;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @PrePersist
    public void setUp() {
        this.date = LocalDateTime.now();
    }
}
