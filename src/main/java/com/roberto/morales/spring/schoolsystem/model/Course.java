package com.roberto.morales.spring.schoolsystem.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "courses")
public class Course{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name")
  @NotBlank
  private String name;

  @Column(name = "description")
  @NotBlank
  private String description;

  @ManyToMany(fetch = FetchType.LAZY,
      cascade = {
          CascadeType.PERSIST,
          CascadeType.MERGE
      },
      mappedBy = "courses")
  @JsonIgnore
  private Set<Student> students = new HashSet<>();

  public Course() {

  }

  public Course(String name, String description){
    this.name = name;
    this.description = description;
  }

  public Course(Long id, String name, String description){
    this.id = id;
    this.name = name;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Student> getStudents() {
    return students;
  }

  public void setStudents(Set<Student> students) {
    this.students = students;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Course{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", students=").append(students);
    sb.append('}');
    return sb.toString();
  }
  
}
