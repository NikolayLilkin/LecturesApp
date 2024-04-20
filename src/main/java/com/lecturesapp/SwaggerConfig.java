package com.lecturesapp;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "LectureApp",
        description = "The LectureApp is a Spring Boot Application that manages operations related to students, " +
                "courses, and their registrations. It interacts with three entities: Student, Course, " +
                "and CourseRegistration to perform CRUD operations and manage relationships. Here's a summary of all endpoints."))
public class SwaggerConfig {
}
