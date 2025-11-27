package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.CourseRepository;
import br.com.academiaDev.application.repositories.EnrollmentRepository;
import br.com.academiaDev.application.repositories.UserRepository;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.entities.Enrollment;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.entities.User;
import br.com.academiaDev.domain.exceptions.BusinessException;
import br.com.academiaDev.domain.exceptions.EnrollmentException;

public class MatricularAlunoUseCase {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository; 

    public MatricularAlunoUseCase(CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    public void execute(String studentEmail, String courseTitle) {
        Course course = courseRepository.findByTitle(courseTitle)
                .orElseThrow(() -> new BusinessException("Course not found: " + courseTitle));
        
        User user = userRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new BusinessException("User not found"));
        if (!(user instanceof Student)) throw new BusinessException("Only students can enroll.");
        Student student = (Student) user;

        boolean alreadyEnrolled = enrollmentRepository.findByStudent(student).stream()
                .anyMatch(e -> e.getCourse().getTitle().equals(courseTitle));
        
        if (alreadyEnrolled) {
            throw new EnrollmentException("Student is already enrolled in this course.");
        }

        int activeEnrollments = enrollmentRepository.countActiveByStudent(student);
        if (!student.getSubscriptionPlan().canEnroll(activeEnrollments)) {
            throw new EnrollmentException("Subscription plan limit reached.");
        }

        Enrollment newEnrollment = new Enrollment(student, course);

        enrollmentRepository.save(newEnrollment);
    }
}