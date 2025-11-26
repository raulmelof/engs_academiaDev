package br.com.academiaDev.application.repositories;

import java.util.List;
import java.util.Optional;

import br.com.academiaDev.domain.entities.Course;

public interface CourseRepository {
    void save(Course course);
    List<Course> findAll();
    Optional<Course> findByTitle(String title);
}