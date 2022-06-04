package com.roberto.morales.spring.schoolsystem;

import com.roberto.morales.spring.schoolsystem.model.Course;
import com.roberto.morales.spring.schoolsystem.repository.CourseRepository;
import com.roberto.morales.spring.schoolsystem.repository.StudentRepository;
import com.roberto.morales.spring.schoolsystem.service.CourseService;
import com.roberto.morales.spring.schoolsystem.service.StudentService;
import com.roberto.morales.spring.schoolsystem.service.impl.CourseServiceImpl;
import com.roberto.morales.spring.schoolsystem.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CourseServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    private CourseService courseService;
    private StudentService studentService;

    @BeforeEach
    public void setup(){
        studentService = new StudentServiceImpl(studentRepository, courseRepository);
        courseService = new CourseServiceImpl(courseRepository, studentRepository);
    }

    @Test
    public void createCourse(){
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).then(AdditionalAnswers.returnsFirstArg());
        Course course = courseService.createCourse(Mocks.buildCourse(1l,"course1","desc"));
        assertNotNull(course);
        Mockito.verify(courseRepository,Mockito.times(1) ).save(Mockito.any(Course.class));
    }

    @Test
    public void updateCourse(){
        Mockito.when(courseRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(Mocks.buildCourse(1l,"name1","desc")));
        Mockito.when(courseRepository.save(Mockito.any(Course.class))).then(AdditionalAnswers.returnsFirstArg());
        Course course = courseService.updateCourse(1L,Mocks.buildCourse(1l,"name1","desc"));
        assertNotNull(course);
        Mockito.verify(courseRepository,Mockito.times(1) ).save(Mockito.any(Course.class));
    }

    @Test
    public void deleteCourse(){
        Long courseId = 1l;
        Mockito.doNothing().when(courseRepository).deleteById(courseId);
        courseService.deleteCourse(1l);
        Mockito.verify(courseRepository,Mockito.times(1) ).deleteById(courseId);
    }

    @Test
    public void getAllCourses(){
        Mockito.when(courseRepository.findAll()).thenReturn(Mocks.buildCourseList());
        List<Course> courseList = courseService.getAllCourses(false);
        assertNotNull(courseList);
        Mockito.verify(courseRepository,Mockito.times(1) ).findAll();
    }

    @Test
    public void getAllCoursesWithNoStudentsAssigned(){
        Mockito.when(courseRepository.findAllByStudentsEmpty()).thenReturn(Mocks.buildCourseList());
        List<Course> courseList = courseService.getAllCourses(true);
        assertNotNull(courseList);
        Mockito.verify(courseRepository,Mockito.times(1) ).findAllByStudentsEmpty();
    }

    @Test
    public void getCoursesByStudent(){
        Long studentId = 1l;
        Mockito.when(studentRepository.existsById(studentId)).thenReturn(true);
        Mockito.when(courseRepository.findCoursesByStudentsId(studentId)).thenReturn(Mocks.buildCourseList());
        List<Course> courseList = courseService.getCoursesByStudent(studentId);
        assertNotNull(courseList);
        Mockito.verify(courseRepository,Mockito.times(1) ).findCoursesByStudentsId(studentId);
    }
    
}
