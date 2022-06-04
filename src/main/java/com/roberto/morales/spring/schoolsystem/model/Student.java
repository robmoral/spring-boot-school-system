package com.roberto.morales.spring.schoolsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  @NotBlank
  private String name;

  @Column(name = "email")
  @Email
  @NotBlank
  private String email;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      })
  @JoinTable(name = "student_courses",
        joinColumns = { @JoinColumn(name = "student_id") },
        inverseJoinColumns = { @JoinColumn(name = "course_id") })
  private Set<Course> courses = new HashSet<>();
  
  public Student() {

  }

  public Student(String name, String email) {
    this.name = name;
    this.email = email;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }
  
  public void addCourse(Course course) {
    this.courses.add(course);
    course.getStudents().add(this);
  }

  
  public void removeCourse(long courseId) {
    Course course = this.courses.stream().filter(t -> t.getId() == courseId).findFirst().orElse(null);
    if (course != null) {
      this.courses.remove(course);
      course.getStudents().remove(this);
    }
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Student{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", courses=").append(courses);
    sb.append('}');
    return sb.toString();
  }
}
