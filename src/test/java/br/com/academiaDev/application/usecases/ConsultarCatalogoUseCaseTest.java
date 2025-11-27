package br.com.academiaDev.application.usecases;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.enums.DifficultyLevel;

@ExtendWith(MockitoExtension.class)
class ConsultarCatalogoUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    private ConsultarCatalogoUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new ConsultarCatalogoUseCase(courseRepository);
    }

    @Test
    void deveBuscarTodosOsCursosDoRepositorio() {
        when(courseRepository.findAll()).thenReturn(List.of(
            new Course("Java", "Desc", "Prof", 10, DifficultyLevel.BEGINNER)
        ));

        useCase.execute();

        verify(courseRepository, times(1)).findAll();
    }
}