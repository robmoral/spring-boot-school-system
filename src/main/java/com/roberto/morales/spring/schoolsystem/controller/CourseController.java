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
public class CourseController {

  @Autowired
  private CourseService courseService;

  @Autowired
  private StudentService studentService;


  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getAllCourses(
          @RequestParam(value = "onlyEmptyCourses", defaultValue = "false") boolean onlyEmptyCourses) {
    return new ResponseEntity<>(courseService.getAllCourses(onlyEmptyCourses), HttpStatus.OK);
  }

  @GetMapping("/courses/{id}")
  public ResponseEntity<Course> getCoursesById(@PathVariable(value = "id") Long id) {
    return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
  }
  
  @GetMapping("/courses/{courseId}/students")
  public ResponseEntity<List<Student>> getAllStudentsByCourseId(@PathVariable(value = "courseId") Long courseId) {
    return new ResponseEntity<>(studentService.getStudentsByCourse(courseId), HttpStatus.OK);
  }

  @PostMapping("/courses")
  public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course) {
    return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
  }

  @PutMapping("/courses/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @Valid @RequestBody Course course) {
    return new ResponseEntity<>(courseService.updateCourse(id, course), HttpStatus.OK);
  }

  @DeleteMapping("/courses/{id}")
  public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
    courseService.deleteCourse(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
