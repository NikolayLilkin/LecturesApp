package com.lecturesapp.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;

@Entity @Getter @Setter @NoArgsConstructor
public class Student {
    @Id @GeneratedValue(strategy=GenerationType.AUTO) @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private int age;

    @OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<CourseRegistration> courseRegistrations;
}