package com.ss.web.app.service;

import com.ss.web.app.model.Student;
import com.ss.web.app.model.Subject;
import com.ss.web.app.repository.EnrollmentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepo enrollmentRepo;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private static List<Student> generateRandomStudents() {
        return Arrays.asList(
                new Student(3L, "KSNCXA", "DCÑAODMSC"),
                new Student(4L, "ABC", "DCKMASD")
        );
    }

    private static List<Subject> generateRandomSubjects() {
        return Arrays.asList(
                new Subject(10L, "Algebra", "None"),
                new Subject(11L, "ALGEBRA 2", "None")
        );
    }

    @DisplayName("Test findAllStudents: Valid collegeId, returns list of students")
    @ParameterizedTest
    @MethodSource("provideCollegeIds")
    public void testFindAllStudents_ValidCollegeId_ReturnListOfStudents(Long collegeId) {
        List<Student> students = generateRandomStudents();
        when(enrollmentRepo.listStudents(collegeId)).thenReturn(students);
        List<Student> result = enrollmentService.findAllStudents(collegeId);
        assertNotNull(result);
        assertEquals(students.size(), result.size());
        verify(enrollmentRepo, times(1)).listStudents(collegeId);
    }

    @DisplayName("Test findAllStudents: Valid collegeId, no students found")
    @Test
    public void testFindAllStudents_ValidCollegeId_NoStudentsFound() {
        Long collegeId = 1L;
        List<Student> students = new ArrayList<>();
        when(enrollmentRepo.listStudents(collegeId)).thenReturn(students);
        List<Student> result = enrollmentService.findAllStudents(collegeId);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(enrollmentRepo, times(1)).listStudents(collegeId);
    }

    @DisplayName("Test findAllSubjects: Valid collegeId, returns list of subjects")
    @ParameterizedTest
    @MethodSource("provideCollegeIds")
    public void testFindAllSubjects_ValidCollegeId_ReturnListOfSubjects(Long collegeId) {
        List<Subject> subjects = generateRandomSubjects();
        when(enrollmentRepo.listSubjects(collegeId)).thenReturn(subjects);
        List<Subject> result = enrollmentService.findAllSubjects(collegeId);
        assertNotNull(result);
        assertEquals(subjects.size(), result.size());
        verify(enrollmentRepo, times(1)).listSubjects(collegeId);
    }

    @DisplayName("Test findAllSubjects: Invalid collegeId, returns null")
    @Test
    public void testFindAllSubjects_InvalidCollegeId_ReturnNull() {
        Long invalidCollegeId = -1L;
        when(enrollmentRepo.listSubjects(invalidCollegeId)).thenReturn(null);
        List<Subject> result = enrollmentService.findAllSubjects(invalidCollegeId);
        assertNull(result);
        verify(enrollmentRepo, times(1)).listSubjects(invalidCollegeId);
    }

    @DisplayName("Test findAllSubjects: Valid collegeId, no subjects found")
    @ParameterizedTest
    @MethodSource("provideCollegeIds")
    public void testFindAllSubjects_ValidCollegeId_NoSubjectsFound(Long collegeId) {
        when(enrollmentRepo.listSubjects(collegeId)).thenReturn(null);
        List<Subject> result = enrollmentService.findAllSubjects(collegeId);
        assertNull(result);
        verify(enrollmentRepo, times(1)).listSubjects(collegeId);
    }

    private static Stream<Long> provideCollegeIds() {
        return Stream.of(1L, 2L, 3L);
    }
}
