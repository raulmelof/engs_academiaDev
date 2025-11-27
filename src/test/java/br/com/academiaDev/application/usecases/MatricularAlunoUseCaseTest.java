package br.com.academiaDev.application.usecases;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.application.repositories.UserRepository;
import br.com.academiaDev.domain.entities.BasicPlan;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.enums.DifficultyLevel;
import br.com.academiaDev.domain.exceptions.BusinessException;
import br.com.academiaDev.domain.exceptions.EnrollmentException;

@ExtendWith(MockitoExtension.class)
class MatricularAlunoUseCaseTest {

    @Mock private CourseRepository courseRepository;
    @Mock private EnrollmentRepository enrollmentRepository;
    @Mock private UserRepository userRepository;

    private MatricularAlunoUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new MatricularAlunoUseCase(courseRepository, enrollmentRepository, userRepository);
    }

    @Test
    void deveMatricularComSucesso() {
        String email = "teste@email.com";
        String titulo = "Java";
        Course curso = new Course(titulo, "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        
        Student alunoMock = new Student("João", email, new BasicPlan());
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(alunoMock));
        
        when(courseRepository.findByTitle(titulo)).thenReturn(Optional.of(curso));
        when(enrollmentRepository.countActiveByStudent(any())).thenReturn(0);
        when(enrollmentRepository.findByStudent(any())).thenReturn(List.of());

        useCase.execute(email, titulo);

        verify(enrollmentRepository, times(1)).save(any(Enrollment.class));
    }

    @Test
    void naoDeveMatricularSePlanoEstourou() {
        String email = "teste@email.com";
        String titulo = "Java";
        Course curso = new Course(titulo, "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        Student alunoMock = new Student("João", email, new BasicPlan()); // Plano básico

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(alunoMock));
        when(courseRepository.findByTitle(titulo)).thenReturn(Optional.of(curso));
        
        when(enrollmentRepository.countActiveByStudent(any())).thenReturn(3); 
        when(enrollmentRepository.findByStudent(any())).thenReturn(List.of());

        EnrollmentException exception = assertThrows(EnrollmentException.class, () -> 
            useCase.execute(email, titulo)
        );
        assertEquals("Subscription plan limit reached.", exception.getMessage());
    }

    @Test
    void naoDeveMatricularSeCursoNaoExiste() {
        when(courseRepository.findByTitle("Inexistente")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> 
            useCase.execute("teste@email.com", "Inexistente")
        );
        assertTrue(exception.getMessage().contains("Course not found"));
    }
}