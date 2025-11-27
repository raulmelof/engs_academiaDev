package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.domain.enums.CourseStatus;

public class ConsultarCatalogoUseCase {
    private final CourseRepository courseRepository;

    public ConsultarCatalogoUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void execute() {
        System.out.println("--- Catálogo de Cursos Ativos ---");
        courseRepository.findAll().stream()
            .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
            .forEach(c -> System.out.println(c.getTitle() + " (" + c.getDifficultyLevel() + ") - " + c.getDurationInHours() + "h"));
    }
}