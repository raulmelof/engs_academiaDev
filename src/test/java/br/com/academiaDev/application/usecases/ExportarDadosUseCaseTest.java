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

@ExtendWith(MockitoExtension.class)
class ExportarDadosUseCaseTest {

    @Mock
    private CourseRepository courseRepository;

    private ExportarDadosUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new ExportarDadosUseCase(courseRepository);
    }

    @Test
    void deveBuscarDadosParaExportacao() {
        when(courseRepository.findAll()).thenReturn(List.of());

        useCase.execute();

        verify(courseRepository, times(1)).findAll();
    }
}