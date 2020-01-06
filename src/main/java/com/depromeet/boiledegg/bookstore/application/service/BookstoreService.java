package com.depromeet.boiledegg.bookstore.application.service;

import com.depromeet.boiledegg.bookstore.domain.entity.Bookstore;
import com.depromeet.boiledegg.bookstore.domain.repository.BookstoreRepository;
import com.depromeet.boiledegg.bookstore.representation.BookstoreSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public final class BookstoreService {

    private final BookstoreRepository repository;

    public Bookstore save(final BookstoreSaveRequest request) {
        return repository.save(Bookstore.builder()
                .name(request.getName())
                .description(request.getDescription())
                .location(request.getLocation())
                .build());
    }

    public Optional<Bookstore> findById(final Long id) {
        return repository.findById(id);
    }

    public Page<Bookstore> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }
}
