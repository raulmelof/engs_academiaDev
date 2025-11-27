package br.com.academiaDev.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import br.com.academiaDev.application.repositories.UserRepository;
import br.com.academiaDev.domain.entities.User;

public class UserRepositoryInMemory implements UserRepository {
    private final Map<String, User> db = new HashMap<>();

    @Override
    public void save(User user) {
        db.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(db.get(email));
    }
}