package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.exceptions.BusinessException;

public class AtualizarProgressoUseCase {

    private final EnrollmentRepository enrollmentRepository;

    public AtualizarProgressoUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String studentEmail, String courseTitle, int newProgress) {
        Enrollment enrollment = enrollmentRepository.findAll().stream()
                .filter(e -> e.getStudent().getEmail().equals(studentEmail) && 
                             e.getCourse().getTitle().equals(courseTitle))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Enrollment not found."));

        enrollment.updateProgress(newProgress);
        
        enrollmentRepository.save(enrollment);
    }
}