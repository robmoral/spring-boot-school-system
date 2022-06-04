package com.roberto.morales.spring.schoolsystem.service.impl;

import com.roberto.morales.spring.schoolsystem.exception.ResourceNotFoundException;
import com.roberto.morales.spring.schoolsystem.exception.ValidationException;
import com.roberto.morales.spring.schoolsystem.model.Course;
import com.roberto.morales.spring.schoolsystem.repository.CourseRepository;
import com.roberto.morales.spring.schoolsystem.repository.StudentRepository;
import com.roberto.morales.spring.schoolsystem.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(new Course(course.getName(), course.getDescription()));
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        Course _course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CourseId " + id + "not found"));
        _course.setName(course.getName());
        return courseRepository.save(_course);
    }

    @Override
    public void deleteCourse(Long id) {
        try {
            courseRepository.deleteById(id);
        }catch(EmptyResultDataAccessException ex){
            throw new ValidationException("There is no course with id :" + id);
        }
    }

    @Override
    public List<Course> getAllCourses(boolean onlyEmptyCourses) {
        if(onlyEmptyCourses){
            return courseRepository.findAllByStudentsEmpty();
        }
       return courseRepository.findAll();
    }

    @Override
    public List<Course> getCoursesByStudent(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Not found Student with id = " + studentId);
        }
        return courseRepository.findCoursesByStudentsId(studentId);
    }
    
    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));
    }
}
