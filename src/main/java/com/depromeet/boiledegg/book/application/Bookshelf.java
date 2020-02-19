package com.depromeet.boiledegg.book.application;

import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchType;

import java.util.List;

public interface Bookshelf {

    BookInformation findByIsbn(final String isbn);

    List<BookInformation> findByTitle(final String title);

    List<BookInformation> find(
            final BookSearchType type,
            final String value
    );
}
