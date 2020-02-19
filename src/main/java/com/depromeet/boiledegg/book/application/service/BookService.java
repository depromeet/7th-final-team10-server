package com.depromeet.boiledegg.book.application.service;

import com.depromeet.boiledegg.book.application.Bookshelf;
import com.depromeet.boiledegg.book.domain.entity.Book;
import com.depromeet.boiledegg.book.domain.repository.BookRepository;
import com.depromeet.boiledegg.book.exception.BookNotFoundException;
import com.depromeet.boiledegg.book.representation.BookPageRequest;
import com.depromeet.boiledegg.book.representation.BookSaveRequest;
import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchType;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository repository;

    private final Bookshelf bookshelf;

    @Transactional(readOnly = true)
    public Optional<Book> findById(final Long id) {
        return repository.findById(id);
    }

    public Book save(final BookSaveRequest request) {
        final var isbn = request.getIsbn();
        verifySingleIsbn(isbn);

        return repository.save(Book.builder()
                .isbn(isbn)
                .bookCategories(request.getBookCategories())
                .build());
    }

    private void verifySingleIsbn(final String isbn) {
        bookshelf.findByIsbn(isbn);
    }

    @Transactional(readOnly = true)
    public Page<Book> findAll(final BookPageRequest pageRequest) {
        return repository.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<Book> findByOwner(
            final long owner,
            final Pageable pageable
    ) {
        return repository.findByOwner(
                owner,
                pageable
        );
    }

    public BookInformation search(final Long id) {
        return findById(id)
                .map(Book::getIsbn)
                .map(bookshelf::findByIsbn)
                .orElseThrow(BookNotFoundException::new);
    }

    public List<BookInformation> search(
            final BookSearchType type,
            final String value
    ) {
        return bookshelf.find(
                type,
                value
        );
    }

    @Transactional
    public Book like(
            final Long id,
            final SessionUser user
    ) {
        return findOrThrow(id).like(user);
    }

    public Book findOrThrow(final Long id) {
        return findById(id).orElseThrow(BookNotFoundException::new);
    }
}
