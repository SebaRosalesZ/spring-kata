package com.ss.web.app.service;

import com.ss.web.app.model.Student;
import com.ss.web.app.repository.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {

  private StudentRepo repo;
  private StudentServiceImpl studentService;

  @BeforeEach
  public void setUp() {
    repo = mock(StudentRepo.class);
    studentService = new StudentServiceImpl(repo);
  }

  @Test
  public void givenNewStudent_whenAddStudent_thenReturnSavedStudent() {
    Student student = new Student(4L, "Ana", "Perez");
    when(repo.save(student)).thenReturn(student);
    Student studentExpected = studentService.addStudent(student);
    assertNotNull(studentExpected);
    assertEquals(student, studentExpected);
  }

  @Test
  public void givenExistingStudents_whenFindAllStudents_thenReturnAllStudents() {
    List<Student> students = new ArrayList<>();
    students.add(new Student(12L, "Sebas", "Rosales"));
    students.add(new Student(3L, "Jose", "Zurita"));
    when(repo.findAll()).thenReturn(students);
    List<Student> allStudents = studentService.findAll();
    assertNotNull(allStudents);
    assertEquals(2, allStudents.size());
    assertEquals(students, allStudents);
  }

  @Test
  public void givenUpdatedStudent_whenEditStudent_thenReturnEditedStudent() {
    Student student = new Student(3L, "Sebas Jose", "Z Rosales");
    when(repo.edit(student)).thenReturn(student);
    Student editedStudent = studentService.edit(student);
    assertNotNull(editedStudent);
    assertEquals(student, editedStudent);
  }

  @Test
  public void givenExistingStudent_whenDeleteStudent_thenDeleteStudent() {
    Student student = new Student(5L, "ABC", "XYZ");
    when(repo.delete(student)).thenReturn(true);
    boolean isDeleted = studentService.delete(student);
    assertTrue(isDeleted);
  }

  @Test
  public void givenNonExistingStudent_whenDeleteStudent_thenStudentNotDeleted() {
    Student student = new Student(10L, "John", "Doe");
    when(repo.delete(student)).thenReturn(false);
    boolean isDeleted = studentService.delete(student);
    assertFalse(isDeleted);
  }
}
