package br.com.academiaDev.application.usecases;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.enums.CourseStatus;
import br.com.academiaDev.domain.enums.DifficultyLevel;

public class GerarRelatoriosUseCase {
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public GerarRelatoriosUseCase(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public Map<DifficultyLevel, List<String>> getCoursesByDifficulty() {
        return courseRepository.findAll().stream()
                .sorted(Comparator.comparing(Course::getTitle))
                .collect(Collectors.groupingBy(
                        Course::getDifficultyLevel,
                        Collectors.mapping(Course::getTitle, Collectors.toList())
                ));
    }

    public Set<String> getUniqueInstructors() {
        return courseRepository.findAll().stream()
                .filter(c -> c.getStatus() == CourseStatus.ACTIVE)
                .map(Course::getInstructorName)
                .collect(Collectors.toSet());
    }

    public Map<String, List<String>> getStudentsByPlan() {
        return enrollmentRepository.findAll().stream()
                .map(Enrollment::getStudent)
                .distinct()
                .collect(Collectors.groupingBy(
                        s -> s.getSubscriptionPlan().getPlanName(),
                        Collectors.mapping(Student::getName, Collectors.toList())
                ));
    }
}