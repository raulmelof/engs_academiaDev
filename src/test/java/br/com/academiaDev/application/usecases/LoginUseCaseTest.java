package br.com.academiaDev.application.usecases;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.UserRepository;
import br.com.academiaDev.domain.entities.Admin;
import br.com.academiaDev.domain.entities.BasicPlan;
import br.com.academiaDev.domain.entities.Student;
import br.com.academiaDev.domain.entities.User;

@ExtendWith(MockitoExtension.class)
class LoginUseCaseTest {

    @Mock private UserRepository userRepository;
    private LoginUseCase useCase;

    @BeforeEach
    public void setUp() {
        useCase = new LoginUseCase(userRepository);
    }

    @Test
    void deveRetornarRoleAdmin() {
        String email = "admin@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new Admin("Admin", email)));

        assertEquals(UserRole.ADMIN, useCase.execute(email));
    }

    @Test
    void deveRetornarRoleStudent() {
        String email = "student@test.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new Student("Student", email, new BasicPlan())));

        assertEquals(UserRole.STUDENT, useCase.execute(email));
    }

    @Test
    void deveRetornarUnknownSeTipoDesconhecido() {
        User unknownUser = new User("Unknown", "test@unknown.com") {}; 
        when(userRepository.findByEmail("test@unknown.com")).thenReturn(Optional.of(unknownUser));

        assertEquals(UserRole.UNKNOWN, useCase.execute("test@unknown.com"));
    }

    @Test
    void deveFalharSeUsuarioNaoExiste() {
        when(userRepository.findByEmail("ghost@test.com")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> 
            useCase.execute("ghost@test.com")
        );
        assertEquals("User not found: ghost@test.com", exception.getMessage());
    }
}