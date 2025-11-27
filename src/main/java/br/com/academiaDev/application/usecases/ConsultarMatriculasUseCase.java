package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.BasicPlan;
import br.com.academiaDev.domain.entities.Student;

public class ConsultarMatriculasUseCase {
    private final EnrollmentRepository enrollmentRepository;

    public ConsultarMatriculasUseCase(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    public void execute(String email) {
        Student student = new Student("Busca", email, new BasicPlan());
        
        enrollmentRepository.findByStudent(student).forEach(e -> 
            System.out.println("Curso: " + e.getCourse().getTitle() + " - Progresso: " + e.getProgressPercentage() + "%")
        );
    }
}