package br.com.academiaDev.infrastructure.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.domain.entities.Course;

public class CourseRepositoryEmMemoria implements CourseRepository {
    private final Map<String, Course> db = new HashMap<>();

    @Override
    public void save(Course course) {
        db.put(course.getTitle(), course);
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return Optional.ofNullable(db.get(title));
    }
}