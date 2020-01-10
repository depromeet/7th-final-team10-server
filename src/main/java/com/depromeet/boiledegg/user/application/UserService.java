package com.depromeet.boiledegg.user.application;

import com.depromeet.boiledegg.user.domain.entity.User;
import com.depromeet.boiledegg.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class UserService {

    private final UserRepository repository;

    public Optional<User> findById(final long id) {
        return repository.findById(id);
    }

    public User save(final User user) {
        return repository.save(user);
    }

    public Optional<User> findByEmail(final String email) {
        return repository.findByEmail(email);
    }
}
