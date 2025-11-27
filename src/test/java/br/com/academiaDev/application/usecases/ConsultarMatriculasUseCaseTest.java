package br.com.academiaDev.application.usecases;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.BasicPlan;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.enums.DifficultyLevel;

@ExtendWith(MockitoExtension.class)
class ConsultarMatriculasUseCaseTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    private ConsultarMatriculasUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new ConsultarMatriculasUseCase(enrollmentRepository);
    }

    @Test
    void deveBuscarMatriculasPeloEstudante() {
        Student student = new Student("Aluno", "email", new BasicPlan());
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        
        when(enrollmentRepository.findByStudent(any())).thenReturn(List.of(
            new Enrollment(student, course)
        ));

        useCase.execute("email@teste.com");

        verify(enrollmentRepository, times(1)).findByStudent(any());
    }
}