package com.depromeet.boiledegg.book.representation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class BookSaveRequest {

    private String isbn;

    @Builder
    public BookSaveRequest(
            final String isbn
    ) {
        this.isbn = isbn;
    }
}
