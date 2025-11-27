package br.com.academiaDev.application.usecases;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.enums.CourseStatus;
import br.com.academiaDev.domain.enums.DifficultyLevel;
import br.com.academiaDev.domain.exceptions.BusinessException;

@ExtendWith(MockitoExtension.class)
class GerenciarStatusCursoUseCaseTest {

    @Mock private CourseRepository courseRepository;
    private GerenciarStatusCursoUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new GerenciarStatusCursoUseCase(courseRepository);
    }

    @Test
    void deveInativarCursoComSucesso() {
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(course));

        useCase.execute("Java", CourseStatus.INACTIVE);

        assertEquals(CourseStatus.INACTIVE, course.getStatus());
        verify(courseRepository).save(course);
    }

    @Test
    void deveAtivarCursoComSucesso() {
        Course course = new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER);
        course.inactivate();
        when(courseRepository.findByTitle("Java")).thenReturn(Optional.of(course));

        useCase.execute("Java", CourseStatus.ACTIVE);

        assertEquals(CourseStatus.ACTIVE, course.getStatus());
        verify(courseRepository).save(course);
    }

    @Test
    void deveFalharSeCursoNaoExiste() {
        when(courseRepository.findByTitle("Inexistente")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> 
            useCase.execute("Inexistente", CourseStatus.ACTIVE)
        );
        
        assertEquals("Course not found", exception.getMessage());
    }
}