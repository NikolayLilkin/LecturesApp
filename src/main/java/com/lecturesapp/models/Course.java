package com.lecturesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity @NoArgsConstructor @Getter @Setter
public class Course {
    @Id @GeneratedValue(strategy= GenerationType.AUTO) @Column
    private Integer id;

    @Column
    private String name;

    @OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE)
    private Set<CourseRegistration> courseRegistrations;
}