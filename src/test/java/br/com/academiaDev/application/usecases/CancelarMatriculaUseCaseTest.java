package br.com.academiaDev.application.usecases;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.BasicPlan;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.enums.DifficultyLevel;
import br.com.academiaDev.domain.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
class CancelarMatriculaUseCaseTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    private CancelarMatriculaUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new CancelarMatriculaUseCase(enrollmentRepository);
    }

    @Test
    void deveCancelarMatriculaComSucesso() {
        String email = "aluno@test.com";
        String titulo = "Java";
        
        Student student = new Student("Aluno", email, new BasicPlan());
        Course course = new Course(titulo, "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Enrollment enrollment = new Enrollment(student, course);

        when(enrollmentRepository.findByStudent(any())).thenReturn(List.of(enrollment));

        useCase.execute(email, titulo);

        verify(enrollmentRepository).delete(enrollment);
    }

    @Test
    void deveFalharSeMatriculaNaoEncontrada() {
        when(enrollmentRepository.findByStudent(any())).thenReturn(List.of());

        BusinessException e = assertThrows(BusinessException.class, () -> 
            useCase.execute("aluno@test.com", "Java")
        );
        assertEquals("Matrícula não encontrada para cancelamento.", e.getMessage());
    }
}