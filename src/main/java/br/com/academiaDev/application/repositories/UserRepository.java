package br.com.academiaDev.application.repositories;

import java.util.Optional;

import br.com.academiaDev.domain.entities.User;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
}