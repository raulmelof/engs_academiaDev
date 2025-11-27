package br.com.academiaDev.application.usecases;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
class AtualizarProgressoUseCaseTest {

    @Mock private EnrollmentRepository enrollmentRepository;
    private AtualizarProgressoUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new AtualizarProgressoUseCase(enrollmentRepository);
    }

    @Test
    void deveAtualizarProgressoCorretamente() {
        String email = "aluno@teste.com";
        String cursoTitulo = "Java";
        
        Student student = new Student("Aluno", email, new BasicPlan());
        Course course = new Course(cursoTitulo, "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Enrollment enrollment = new Enrollment(student, course);

        when(enrollmentRepository.findAll()).thenReturn(List.of(enrollment));

        useCase.execute(email, cursoTitulo, 50);

        assertEquals(50, enrollment.getProgressPercentage());
        verify(enrollmentRepository).save(enrollment);
    }

    @Test
    void deveFalharSeMatriculaNaoEncontrada() {
        when(enrollmentRepository.findAll()).thenReturn(List.of());

        BusinessException exception = assertThrows(BusinessException.class, () -> 
            useCase.execute("email", "curso", 50)
        );
        
        assertEquals("Enrollment not found.", exception.getMessage());
    }
}