package com.lecturesapp.repository;

import com.lecturesapp.models.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Integer> {
    boolean existsByStudentIdAndCourseId(Integer studentId, Integer courseId);
    CourseRegistration findByStudentIdAndCourseId(Integer studentId,Integer courseId);
}