package com.depromeet.boiledegg.book.representation;

import com.depromeet.boiledegg.book.domain.BookCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class BookSaveRequest {

    private String isbn;

    private Set<BookCategory> bookCategories;

    @Builder
    public BookSaveRequest(
            final String isbn,
            final Set<BookCategory> bookCategories
    ) {
        this.isbn = isbn;
        this.bookCategories = bookCategories;
    }
}
