package com.lecturesapp.dtos;

import lombok.Data;

@Data
public class CoursesRegistrationsDTO {
    private Integer courseRegistrationId;
    private String studentName;
    private String courseName;

    public CoursesRegistrationsDTO(Integer courseRegistrationId, String studentName, String courseName) {
        this.courseRegistrationId = courseRegistrationId;
        this.studentName = studentName;
        this.courseName = courseName;
    }
}