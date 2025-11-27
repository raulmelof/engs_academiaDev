package br.com.academiaDev.domain.entities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import br.com.academiaDev.domain.enums.DifficultyLevel;
import br.com.academiaDev.domain.exceptions.EnrollmentException;

class EnrollmentTest {

    @Test
    void naoDeveCriarMatriculaEmCursoInativo() {
        Student student = new Student("Nome", "email", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        course.inactivate(); 

        EnrollmentException e = assertThrows(EnrollmentException.class, () -> new Enrollment(student, course));
        assertEquals("Cannot enroll in an INACTIVE course.", e.getMessage());
    }

    @Test
    void naoDeveAceitarProgressoInvalido() {
        Student student = new Student("Nome", "email", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Enrollment enrollment = new Enrollment(student, course);

        EnrollmentException e1 = assertThrows(EnrollmentException.class, () -> enrollment.updateProgress(-1));
        assertEquals("Progress must be between 0 and 100.", e1.getMessage());

        EnrollmentException e2 = assertThrows(EnrollmentException.class, () -> enrollment.updateProgress(101));
        assertEquals("Progress must be between 0 and 100.", e2.getMessage());
        
        assertDoesNotThrow(() -> enrollment.updateProgress(0));
        assertDoesNotThrow(() -> enrollment.updateProgress(100));
    }
}