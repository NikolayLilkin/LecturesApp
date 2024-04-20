package com.lecturesapp.controllers;

import com.lecturesapp.dtos.*;
import com.lecturesapp.models.*;
import com.lecturesapp.repository.CourseRegistrationRepository;
import com.lecturesapp.repository.CourseRepository;
import com.lecturesapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class CoursesController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    public CoursesController(StudentRepository studentRepository, CourseRepository courseRepository, CourseRegistrationRepository courseRegistrationRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentDTOList = new ArrayList<>();
        for (Student student : students) {
            studentDTOList.add(
                    new StudentDTO(
                            student.getId(),
                            student.getName(),
                            student.getAge()
                    )
            );
        }
        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    @GetMapping("/courses")
    public ResponseEntity<List<CourseDTO>> getCourses() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDTO> courseDTOList = new ArrayList<>();
        for (Course course : courses) {
            courseDTOList.add(
                    new CourseDTO(course.getId(),
                            course.getName()
                    )
            );
        }
        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    @PostMapping("/addStudent")
    public ResponseEntity<StudentDTO> addStudent(@RequestBody Student student) {
        Student newStudent = studentRepository.save(student);
        StudentDTO studentDTO = new StudentDTO(
                newStudent.getId(),
                newStudent.getName(),
                newStudent.getAge()
        );
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping("/addCourse")
    public ResponseEntity<CourseDTO> addCourse(@RequestBody Course course) {
        Course newCourse = courseRepository.save(course);
        CourseDTO courseDTO = new CourseDTO(
                newCourse.getId(),
                newCourse.getName()
        );
        return new ResponseEntity<>(courseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable("studentId") Integer id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return new ResponseEntity<>("Student deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable("courseId") Integer id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return new ResponseEntity<>("Course deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deregisterFromCourse")
    public ResponseEntity<String> deleteCourseRegistration(@RequestBody DeregisterDTO deregisterDTO) {
        return unregisterStudentFromCourse(deregisterDTO.getStudentId(), deregisterDTO.getCourseId());
    }

    @PutMapping("/modifyStudent/{studentId}")
    public ResponseEntity<String> modifyStudent(@PathVariable(name = "studentId") Integer studentId, @RequestBody Student newStudent) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setName(newStudent.getName());
            student.setAge(newStudent.getAge());
            studentRepository.save(student);
            return new ResponseEntity<>("Student modified successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/modifyCourse/{courseId}")
    public ResponseEntity<String> modifyCourse(@PathVariable(name = "courseId") Integer courseId, @RequestBody Course newCourse) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setName(newCourse.getName());
            courseRepository.save(course);
            return new ResponseEntity<>("Lecture modified successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Lecture not found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/enrollStudent")
    public ResponseEntity<String> enrollStudent(@RequestBody EnrollDTO enrollDTO) {
        if (courseRegistrationRepository.existsByStudentIdAndCourseId(enrollDTO.getStudentId(), enrollDTO.getCourseId())) {
            return new ResponseEntity<>("Student is already enrolled for this course", HttpStatus.BAD_REQUEST);
        }

        Optional<Course> courseOpt = courseRepository.findById(enrollDTO.getCourseId());
        Optional<Student> studentOpt = studentRepository.findById(enrollDTO.getStudentId());

        if (courseOpt.isPresent() && studentOpt.isPresent()) {
            Student student = studentOpt.get();
            Course course = courseOpt.get();

            CourseRegistration courseRegistration = new CourseRegistration(
                    student,
                    course
            );
            courseRegistrationRepository.save(courseRegistration);
            return new ResponseEntity<>("Student enrolled for " + course.getName(), HttpStatus.OK);
        } else if (studentOpt.isEmpty()) {
            return new ResponseEntity<>("Student not found", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("Course not found", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCoursesRegistrations")
    public ResponseEntity<List<CoursesRegistrationsDTO>> getCoursesRegistrations() {
        List<CourseRegistration> courseRegistrations = courseRegistrationRepository.findAll();
        List<CoursesRegistrationsDTO> courseDTOList = new ArrayList<>();
        for (CourseRegistration courseRegistration : courseRegistrations) {
            courseDTOList.add(
                    new CoursesRegistrationsDTO(
                            courseRegistration.getId(),
                            courseRegistration.getStudent().getName(),
                            courseRegistration.getCourse().getName()
                    )
            );
        }
        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    public ResponseEntity<String> unregisterStudentFromCourse(Integer studentId, Integer courseId) {
        CourseRegistration registration = courseRegistrationRepository.findByStudentIdAndCourseId(studentId, courseId);
        if (registration != null) {
            registration.getStudent().getCourseRegistrations().remove(registration);
            registration.getCourse().getCourseRegistrations().remove(registration);
            courseRegistrationRepository.delete(registration);
            return new ResponseEntity<>("Student deregistered", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Student not registered for this course", HttpStatus.OK);
        }
    }
}