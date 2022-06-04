package com.roberto.morales.spring.schoolsystem.service.impl;

import com.roberto.morales.spring.schoolsystem.exception.ResourceNotFoundException;
import com.roberto.morales.spring.schoolsystem.exception.ValidationException;
import com.roberto.morales.spring.schoolsystem.model.Course;
import com.roberto.morales.spring.schoolsystem.model.Student;
import com.roberto.morales.spring.schoolsystem.repository.CourseRepository;
import com.roberto.morales.spring.schoolsystem.repository.StudentRepository;
import com.roberto.morales.spring.schoolsystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    private static Integer MAX_COURSES_PER_STUDENT = 5;
    private static Integer MAX_STUDENTS_PER_COURSE = 50;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(new Student(student.getName(), student.getEmail()));
    }

    @Override
    public Student updateStudent(Long id, Student student) {
        Student _student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));
        _student.setName(student.getName());
        _student.setEmail(student.getEmail());
        return studentRepository.save(_student);
    }

    @Override
    public void deleteStudent(Long id) {
        try {
            studentRepository.deleteById(id);
        }catch(EmptyResultDataAccessException ex){
            throw new ValidationException("There is no student with id :" + id);
        }
    }

    @Override
    public Student addCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + studentId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
        if (student.getCourses().size() >= MAX_COURSES_PER_STUDENT) {
            throw new ValidationException("The student is already enrolled in " + MAX_COURSES_PER_STUDENT + " courses");
        }
        if(course.getStudents().size() >= MAX_STUDENTS_PER_COURSE) {
            throw new ValidationException("The course has already " + MAX_STUDENTS_PER_COURSE + " students enrolled.");
        }
        student.addCourse(course);
        student = studentRepository.save(student);
        return student;
    }

    @Override
    public Student removeCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + studentId));
        courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
        student.removeCourse(courseId);
        student = studentRepository.save(student);
        return student;
    }

    @Override
    public List<Student> getAllStudents(boolean onlyStudentsNotAssigned) {
        if(onlyStudentsNotAssigned){
            return studentRepository.findAllByCoursesEmpty();
        }
        return studentRepository.findAll();
    }

    @Override
    public List<Student> getStudentsByCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResourceNotFoundException("Not found Course with courseId = " + courseId);
        }
        return studentRepository.findStudentsByCoursesId(courseId);
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));
    }

}
