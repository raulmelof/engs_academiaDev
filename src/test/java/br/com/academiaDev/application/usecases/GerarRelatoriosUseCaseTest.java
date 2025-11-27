package br.com.academiaDev.application.usecases;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.enums.DifficultyLevel;

@ExtendWith(MockitoExtension.class)
class GerarRelatoriosUseCaseTest {

    @Mock private CourseRepository courseRepository;
    @Mock private EnrollmentRepository enrollmentRepository;

    private GerarRelatoriosUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new GerarRelatoriosUseCase(courseRepository, enrollmentRepository);
    }

    @Test
    void deveAgruparCursosPorDificuldade() {
        Course c1 = new Course("Java", "Desc", "Prof A", 10, DifficultyLevel.BEGINNER);
        Course c2 = new Course("Spring", "Desc", "Prof B", 10, DifficultyLevel.ADVANCED);
        Course c3 = new Course("Python", "Desc", "Prof C", 10, DifficultyLevel.BEGINNER);
        
        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2, c3));

        Map<DifficultyLevel, List<String>> resultado = useCase.getCoursesByDifficulty();

        assertEquals(2, resultado.get(DifficultyLevel.BEGINNER).size()); // Java e Python
        assertEquals(1, resultado.get(DifficultyLevel.ADVANCED).size()); // Spring
        assertTrue(resultado.get(DifficultyLevel.BEGINNER).contains("Java"));
    }

    @Test
    void deveListarInstrutoresUnicos() {
        Course c1 = new Course("Java", "Desc", "Prof A", 10, DifficultyLevel.BEGINNER);
        Course c2 = new Course("Spring", "Desc", "Prof A", 10, DifficultyLevel.ADVANCED); // Mesmo prof
        
        when(courseRepository.findAll()).thenReturn(Arrays.asList(c1, c2));

        Set<String> instrutores = useCase.getUniqueInstructors();

        assertEquals(1, instrutores.size());
        assertTrue(instrutores.contains("Prof A"));
    }
}