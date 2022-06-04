package com.roberto.morales.spring.schoolsystem;


import com.roberto.morales.spring.schoolsystem.model.Course;
import com.roberto.morales.spring.schoolsystem.model.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Mocks {

    public static Student buildStudentWith5Courses(String name, String email){
        Student student = new Student(name, email);
        for(int i = 0; i < 5; i++) {
            student.addCourse(new Course("subject" + i, "description"));
        }
        return student;
    }

    public static Course buildCourseWith50Students(String name, String description){
        Course course = new Course(name,description);
        Set<Student> students = new HashSet<>();
        for(int i = 0; i < 50; i++) {
            students.add(new Student("name" + i, "email"+i +"@email.com"));
        }
        course.setStudents(students);
        return course;
    }
    
    public static Student buildStudent(String name, String email){
        return new Student(name, email);
    }

    public static List<Student> buildStudentList() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("student1", "email1@email.com"));
        studentList.add(new Student("student2", "email2@email.com"));
        return studentList;
    }

    public static Course buildCourse(Long id, String name, String description){
        return new Course(id,name, description);
    }

    public static List<Course> buildCourseList() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("student1", "description"));
        courseList.add(new Course("student2", "description"));
        return courseList;
    }

}
