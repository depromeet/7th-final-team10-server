package com.depromeet.boiledegg.book.application.service;

import com.depromeet.boiledegg.book.domain.entity.Book;
import com.depromeet.boiledegg.book.domain.repository.BookRepository;
import com.depromeet.boiledegg.book.exception.BookNotFoundException;
import com.depromeet.boiledegg.book.exception.InvalidIsbnException;
import com.depromeet.boiledegg.book.representation.BookSaveRequest;
import com.depromeet.boiledegg.common.infrastructure.book.BookClient;
import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchOption;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchType;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookService {

    private static final int UNIQUE_BOOKS_SIZE = 1;
    private static final int FIRST_INDEX = 0;

    private final BookRepository repository;

    private final BookClient bookClient;

    public Optional<Book> findById(final Long id) {
        return repository.findById(id);
    }

    public Book save(final BookSaveRequest request) {
        final var isbn = request.getIsbn();
        verifySingleIsbn(isbn);

        return repository.save(Book.builder()
                .isbn(isbn)
                .build());
    }

    private void verifySingleIsbn(final String isbn) {
        final var books = search(BookSearchOption.isbn(isbn));

        if (books.size() != UNIQUE_BOOKS_SIZE) {
            throw new InvalidIsbnException();
        }
    }

    public Page<Book> findAll(final Pageable pageable) {
        return repository.findAll(pageable);
    }

    public BookInformation search(final Long id) {
        return findById(id)
                .map(Book::getIsbn)
                .map(BookSearchOption::isbn)
                .map(this::search)
                .map(bookInformation -> bookInformation.get(FIRST_INDEX))
                .orElseThrow(BookNotFoundException::new);
    }

    public List<BookInformation> search(
            final BookSearchType type,
            final String value
    ) {
        return search(BookSearchOption.builder()
                .type(type)
                .value(value)
                .build());
    }

    @SneakyThrows
    private List<BookInformation> search(final BookSearchOption option) {
        return bookClient.search(option)
                .getBooks();
    }

    @Transactional
    public Book like(
            final Long id,
            final SessionUser sessionUser
    ) {
        return findOrThrow(id).like(sessionUser);
    }

    public Book findOrThrow(final Long id) {
        return findById(id).orElseThrow(BookNotFoundException::new);
    }
}
