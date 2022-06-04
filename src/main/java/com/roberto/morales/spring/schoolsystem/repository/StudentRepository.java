package com.roberto.morales.spring.schoolsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.roberto.morales.spring.schoolsystem.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

  List<Student> findStudentsByCoursesId(Long courseId);

  List<Student> findAllByCoursesEmpty();

}