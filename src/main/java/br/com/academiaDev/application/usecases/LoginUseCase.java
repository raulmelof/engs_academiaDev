package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.UserRepository;
import br.com.academiaDev.domain.entities.Admin;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.entities.User;

public class LoginUseCase {
    private final UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRole execute(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        if (user instanceof Admin) {
            return UserRole.ADMIN;
        } else if (user instanceof Student) {
            return UserRole.STUDENT;
        } else {
            return UserRole.UNKNOWN;
        }
    }
}