package com.depromeet.boiledegg.transaction.application.service;

import com.depromeet.boiledegg.book.application.service.BookService;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.transaction.domain.entity.Transaction;
import com.depromeet.boiledegg.transaction.domain.repository.TransactionRepository;
import com.depromeet.boiledegg.transaction.exception.CannotBorrowMyBookException;
import com.depromeet.boiledegg.transaction.exception.TransactionNotFoundException;
import com.depromeet.boiledegg.transaction.representation.TransactionSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final BookService bookService;

    private final TransactionRepository repository;

    public Transaction save(
            final SessionUser user,
            final TransactionSaveRequest request
    ) {
        final var book = bookService.findOrThrow(request.getBookId());
        if (book.matchOwner(user)) {
            throw new CannotBorrowMyBookException();
        }

        return repository.save(Transaction.builder()
                .book(book)
                .build());
    }

    public Optional<Transaction> findById(final Long id) {
        return repository.findById(id);
    }

    public Page<Transaction> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    @Transactional
    public Transaction confirm(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).confirm(user);
    }

    @Transactional
    public Transaction reject(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).reject(user);
    }

    @Transactional
    public Transaction cancel(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).cancel(user);
    }

    @Transactional
    public Transaction returns(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).returns(user);
    }

    @Transactional
    public Transaction take(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).take(user);
    }

    private Transaction findOrThrow(final Long id) {
        return findById(id).orElseThrow(TransactionNotFoundException::new);
    }
}
