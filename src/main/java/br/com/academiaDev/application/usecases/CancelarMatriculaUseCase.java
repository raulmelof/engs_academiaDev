package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.BasicPlan;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.exceptions.BusinessException;

public class CancelarMatriculaUseCase {
    
    private final EnrollmentRepository enrollmentRepository;

    public CancelarMatriculaUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String emailStudent, String courseTitle) {
        Student studentSearch = new Student("Busca", emailStudent, new BasicPlan());

        Enrollment enrollmentToDelete = enrollmentRepository.findByStudent(studentSearch).stream()
                .filter(e -> e.getCourse().getTitle().equals(courseTitle))
                .findFirst()
                .orElseThrow(() -> new BusinessException("Matrícula não encontrada para cancelamento."));

        enrollmentRepository.delete(enrollmentToDelete);
        System.out.println("Matrícula cancelada com sucesso! Vaga liberada.");
    }
}