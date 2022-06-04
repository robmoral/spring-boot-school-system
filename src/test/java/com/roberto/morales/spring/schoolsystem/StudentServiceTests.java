package com.roberto.morales.spring.schoolsystem;

import com.roberto.morales.spring.schoolsystem.exception.ValidationException;
import com.roberto.morales.spring.schoolsystem.model.Student;
import com.roberto.morales.spring.schoolsystem.repository.CourseRepository;
import com.roberto.morales.spring.schoolsystem.repository.StudentRepository;
import com.roberto.morales.spring.schoolsystem.service.StudentService;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    private StudentService studentService;

    @BeforeEach
    public void setup(){
        studentService = new StudentServiceImpl(studentRepository, courseRepository);
    }

    @Test
    public void createStudent(){
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).then(AdditionalAnswers.returnsFirstArg());
        Student student = studentService.createStudent(new Student("student1", "email@email.com"));
        assertNotNull(student);
        Mockito.verify(studentRepository,Mockito.times(1) ).save(Mockito.any(Student.class));
    }

    @Test
    public void updateStudent(){
        Mockito.when(studentRepository.findById(Mockito.anyLong())).
                thenReturn(Optional.of(Mocks.buildStudent("name1", "email@email.com")));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).then(AdditionalAnswers.returnsFirstArg());
        Student student = studentService.updateStudent(1L,Mocks.buildStudent("name1", "email@email.com"));
        assertNotNull(student);
        Mockito.verify(studentRepository,Mockito.times(1) ).save(Mockito.any(Student.class));
    }

    @Test
    public void deleteStudent(){
        Long studentId = 1l;
        Mockito.doNothing().when(studentRepository).deleteById(studentId);
        studentService.deleteStudent(1l);
        Mockito.verify(studentRepository,Mockito.times(1) ).deleteById(studentId);
    }

    @Test
    public void getAllStudents(){
        Mockito.when(studentRepository.findAll()).thenReturn(Mocks.buildStudentList());
        List<Student> studentList = studentService.getAllStudents(false);
        assertNotNull(studentList);
        Mockito.verify(studentRepository,Mockito.times(1) ).findAll();
    }

    @Test
    public void getAllStudentsWithNoCoursesAssigned(){
        Mockito.when(studentRepository.findAllByCoursesEmpty()).thenReturn(Mocks.buildStudentList());
        List<Student> studentList = studentService.getAllStudents(true);
        assertNotNull(studentList);
        Mockito.verify(studentRepository,Mockito.times(1) ).findAllByCoursesEmpty();
    }

    @Test
    public void getStudentsByCourse(){
        Long courseId = 1l;
        Mockito.when(courseRepository.existsById(courseId)).thenReturn(true);
        Mockito.when(studentRepository.findStudentsByCoursesId(courseId)).thenReturn(Mocks.buildStudentList());
        List<Student> courseList = studentService.getStudentsByCourse(courseId);
        assertNotNull(courseList);
        Mockito.verify(studentRepository,Mockito.times(1) ).findStudentsByCoursesId(courseId);
    }

    @Test
    public void addCourse(){
        Long courseId = 1l;
        Long studentId = 2l;
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(Mocks.buildStudent("name1", "email@email.com")));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(Mocks.buildCourse(courseId, "name", "desc")));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).then(AdditionalAnswers.returnsFirstArg());
        Student student = studentService.addCourse(studentId, courseId);
        assertNotNull(student);
        //Verify the course was added to the student
        assertTrue(student.getCourses().stream().map(course -> course.getId()).collect(Collectors.toSet()).contains(courseId));
        Mockito.verify(studentRepository,Mockito.times(1) ).save(Mockito.any(Student.class));
    }

    @Test
    public void removeCourse(){
        Long courseId = 1l;
        Long studentId = 2l;
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(Mocks.buildStudent("name1", "email@email.com")));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(Mocks.buildCourse(courseId, "name", "desc")));
        Mockito.when(studentRepository.save(Mockito.any(Student.class))).then(AdditionalAnswers.returnsFirstArg());
        Student student = studentService.addCourse(studentId, courseId);
        assertNotNull(student);
        assertTrue(student.getCourses().stream().map(course -> course.getId()).collect(Collectors.toSet()).contains(courseId));
        student = studentService.removeCourse(studentId, courseId);
        assertNotNull(student);
        assertFalse(student.getCourses().stream().map(course -> course.getId()).collect(Collectors.toSet()).contains(courseId));
    }


    @Test
    public void addCourseFailDueStudentHasMaxCourses(){
        Long courseId = 1l;
        Long studentId = 2l;
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(Mocks.buildStudentWith5Courses("name1", "email@email.com")));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(Mocks.buildCourse(courseId, "name","desc")));
        Exception exception = assertThrows(ValidationException.class,
                () -> { studentService.addCourse(studentId,courseId); });
        assertEquals(exception.getMessage(),"The student is already enrolled in 5 courses");
    }

    @Test
    public void addCourseFailDueCourseHasMaxtudents(){
        Long courseId = 1l;
        Long studentId = 2l;
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(Mocks.buildStudent("name1", "email@email.com")));
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(Mocks.buildCourseWith50Students("name","desc")));
        Exception exception = assertThrows(ValidationException.class,
                () -> { studentService.addCourse(studentId,courseId); });
        assertEquals(exception.getMessage(),"The course has already 50 students enrolled.");
    }

}
