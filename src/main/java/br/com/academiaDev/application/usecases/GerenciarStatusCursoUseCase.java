package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.enums.CourseStatus;
import br.com.academiaDev.domain.exceptions.BusinessException;

public class GerenciarStatusCursoUseCase {
    
    private final CourseRepository courseRepository;

    public GerenciarStatusCursoUseCase(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public void execute(String title, CourseStatus newStatus) {
        Course course = courseRepository.findByTitle(title)
                .orElseThrow(() -> new BusinessException("Course not found"));
        
        if (newStatus == CourseStatus.ACTIVE) {
            course.activate();
        } else {
            course.inactivate();
        }
        
        courseRepository.save(course);
    }
}