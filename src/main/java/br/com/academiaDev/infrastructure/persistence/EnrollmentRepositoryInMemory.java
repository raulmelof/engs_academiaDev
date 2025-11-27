package br.com.academiaDev.infrastructure.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;

public class EnrollmentRepositoryInMemory implements EnrollmentRepository {
    private final List<Enrollment> db = new ArrayList<>();

    @Override
    public void save(Enrollment enrollment) {
        db.add(enrollment);
    }

    @Override
    public List<Enrollment> findAll() {
        return new ArrayList<>(db);
    }

    @Override
    public List<Enrollment> findByStudent(Student student) {
        return db.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public int countActiveByStudent(Student student) {
        return (int) db.stream()
                .filter(e -> e.getStudent().getEmail().equals(student.getEmail()))
                .filter(e -> e.getCourse().getStatus() == br.com.academiaDev.domain.enums.CourseStatus.ACTIVE)
                .count();
    }
}
