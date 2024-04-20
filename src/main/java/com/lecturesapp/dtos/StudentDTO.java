package com.lecturesapp.dtos;

import com.lecturesapp.models.Course;
import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
public class StudentDTO {
    private Integer id;
    private String name;
    private int age;
}