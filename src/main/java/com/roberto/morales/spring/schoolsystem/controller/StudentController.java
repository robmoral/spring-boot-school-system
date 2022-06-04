package com.roberto.morales.spring.schoolsystem.controller;

import java.util.List;

import com.roberto.morales.spring.schoolsystem.model.Course;
import com.roberto.morales.spring.schoolsystem.model.Student;
import com.roberto.morales.spring.schoolsystem.service.CourseService;
import com.roberto.morales.spring.schoolsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentService studentService;
    
    @Autowired
    CourseService courseService;
    
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(
        @RequestParam(value = "onlyStudentsNotAssigned", defaultValue = "false") boolean onlyStudentsNotAssigned) {
        return new ResponseEntity<>(studentService.getAllStudents(onlyStudentsNotAssigned), HttpStatus.OK);
    }
    
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        return new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
    }
    
    @GetMapping("/students/{studentId}/courses")
    public ResponseEntity<List<Course>> getAllCoursesByStudentId(@PathVariable(value = "studentId") Long studentId) {
        return new ResponseEntity<>(courseService.getCoursesByStudent(studentId), HttpStatus.OK);
    }
    
    @PostMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> addCourse(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "courseId") Long courseId) {
        return new ResponseEntity<>(studentService.addCourse(studentId, courseId), HttpStatus.CREATED);
    }

    @DeleteMapping("/students/{studentId}/courses/{courseId}")
    public ResponseEntity<Student> removeCourse(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "courseId") Long courseId) {
        return new ResponseEntity<>(studentService.removeCourse(studentId, courseId), HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody @Valid Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }
    
    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody @Valid Student student) {
        return new ResponseEntity<>(studentService.updateStudent(id, student), HttpStatus.OK);
    }
    
    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}