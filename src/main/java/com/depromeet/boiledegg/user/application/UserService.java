package com.depromeet.boiledegg.user.application;

import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.user.domain.entity.User;
import com.depromeet.boiledegg.user.domain.repository.UserRepository;
import com.depromeet.boiledegg.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public User save(final User user) {
        return repository.save(user);
    }

    @Transactional(readOnly = true)
    public Optional<User> findById(final long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(final String email) {
        return repository.findByEmail(email);
    }

    public User findOrThrow(final long id) {
        return findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public User update(
            final SessionUser sessionUser,
            final User user
    ) {
        return findOrThrow(sessionUser.getId()).update(user);
    }

    private User findOrThrow(final Long id) {
        return findById(id).orElseThrow(UserNotFoundException::new);
    }
}
