package com.lecturesapp.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CourseRegistration {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) @Column
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseRegistration(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}