package com.depromeet.boiledegg.bookstore.application.service;

import com.depromeet.boiledegg.bookstore.domain.entity.Bookstore;
import com.depromeet.boiledegg.bookstore.domain.repository.BookstoreRepository;
import com.depromeet.boiledegg.bookstore.exception.BookstoreNotFoundException;
import com.depromeet.boiledegg.bookstore.representation.BookstoreSaveRequest;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.common.representation.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookstoreService {

    private final BookstoreRepository repository;

    public Bookstore save(final BookstoreSaveRequest request) {
        return repository.save(Bookstore.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .build());
    }

    @Transactional(readOnly = true)
    public Optional<Bookstore> findById(final Long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public Page<Bookstore> findAll(final PageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Transactional
    public Bookstore like(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).like(user);
    }

    private Bookstore findOrThrow(final Long id) {
        return findById(id).orElseThrow(BookstoreNotFoundException::new);
    }
}
