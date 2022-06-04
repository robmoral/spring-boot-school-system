package com.roberto.morales.spring.schoolsystem.service;

import com.roberto.morales.spring.schoolsystem.model.Student;

import java.util.List;

public interface StudentService {

    Student createStudent(Student student);

    Student updateStudent(Long id, Student student);

    void deleteStudent(Long id);

    Student addCourse(Long id, Long courseId);

    Student removeCourse(Long id, Long courseId);

    List<Student> getAllStudents(boolean onlyStudentsNotAssigned);

    List<Student> getStudentsByCourse(Long courseId);

    Student getStudent(Long id);

}
