package br.com.academiaDev.application.repositories;

import java.util.List;

import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;

public interface EnrollmentRepository {
    void save(Enrollment enrollment);
    List<Enrollment> findAll();
    List<Enrollment> findByStudent(Student student);
    int countActiveByStudent(Student student);
    void delete(Enrollment enrollment);
}