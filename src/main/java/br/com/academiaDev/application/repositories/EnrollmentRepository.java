package br.com.academiaDev.application.repositories;

import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import java.util.List;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findAll();
    List<Enrollment> findByStudent(Student student);
    
    int countActiveByStudent(Student student);
}