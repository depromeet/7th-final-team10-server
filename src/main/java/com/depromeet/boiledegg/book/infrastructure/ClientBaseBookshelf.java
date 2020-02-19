package com.depromeet.boiledegg.book.infrastructure;

import com.depromeet.boiledegg.book.application.Bookshelf;
import com.depromeet.boiledegg.book.exception.InvalidIsbnException;
import com.depromeet.boiledegg.common.infrastructure.book.BookClient;
import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchOption;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ClientBaseBookshelf implements Bookshelf {

    private static final int UNIQUE_BOOKS_SIZE = 1;
    private static final int FIRST_INDEX = 0;

    private final BookClient bookClient;

    @SneakyThrows
    @Override
    public BookInformation findByIsbn(final String isbn) {
        return sortOutOnlyBook(isbn);
    }

    private BookInformation sortOutOnlyBook(final String isbn) {
        final var books = search(BookSearchOption.isbn(isbn));
        verifySingleBook(books);

        return books.get(FIRST_INDEX);
    }

    private void verifySingleBook(final List<BookInformation> books) {
        if (books.size() != UNIQUE_BOOKS_SIZE) {
            throw new InvalidIsbnException();
        }
    }

    @Override
    public List<BookInformation> findByTitle(final String title) {
        return search(BookSearchOption.title(title));
    }

    @Override
    public List<BookInformation> find(
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
}
