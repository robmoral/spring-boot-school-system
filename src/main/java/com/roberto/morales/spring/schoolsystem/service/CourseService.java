package com.roberto.morales.spring.schoolsystem.service;

import com.roberto.morales.spring.schoolsystem.model.Course;
import com.roberto.morales.spring.schoolsystem.model.Student;

import java.util.List;

public interface CourseService {

    Course createCourse(Course course);

    Course updateCourse(Long id, Course course);

    void deleteCourse(Long id);

    List<Course> getAllCourses(boolean onlyEmptyCourses);

    List<Course> getCoursesByStudent(Long studentId);
    
    Course getCourse(Long id);
}
