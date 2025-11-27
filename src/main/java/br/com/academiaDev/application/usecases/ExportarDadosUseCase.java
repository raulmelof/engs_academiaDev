package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.infrastructure.utils.GenericCsvExporter;

public class ExportarDadosUseCase {
    private final CourseRepository courseRepository;

    public ExportarDadosUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void execute() {
        GenericCsvExporter.exportToCsv(courseRepository.findAll());
    }
}