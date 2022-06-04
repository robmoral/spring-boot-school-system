package com.roberto.morales.spring.schoolsystem.repository;

import com.roberto.morales.spring.schoolsystem.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {

  List<Course> findCoursesByStudentsId(Long studentId);

  List<Course> findAllByStudentsEmpty();

}
