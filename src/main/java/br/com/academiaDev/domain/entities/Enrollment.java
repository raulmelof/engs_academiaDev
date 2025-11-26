package br.com.academiaDev.domain.entities;

import java.time.LocalDateTime;

import br.com.academiaDev.domain.exceptions.EnrollmentException;

public class Enrollment {
    private Student student; 
    private Course course;
    private int progressPercentage;
    private LocalDateTime enrollmentDate;

    public Enrollment(Student student, Course course) {
        if (course.getStatus() != br.com.academiaDev.domain.enums.CourseStatus.ACTIVE) {
            throw new EnrollmentException("Cannot enroll in an INACTIVE course.");
        }
        this.student = student;
        this.course = course;
        this.progressPercentage = 0;
        this.enrollmentDate = LocalDateTime.now();
    }

    public void updateProgress(int newProgress) {
        if (newProgress < 0 || newProgress > 100) {
            throw new EnrollmentException("Progress must be between 0 and 100.");
        }
        this.progressPercentage = newProgress;
    }

    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public int getProgressPercentage() { return progressPercentage; }
    public LocalDateTime getEnrollmentDate() { return enrollmentDate; }
}