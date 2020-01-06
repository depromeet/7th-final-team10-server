package com.depromeet.boiledegg.common.infrastructure.book;

import lombok.Builder;
import lombok.Data;

@Data
public final class BookSearchOption {

    private final BookSearchType type;

    private final String value;

    @Builder
    public BookSearchOption(
            final BookSearchType type,
            final String value
    ) {
        this.type = type;
        this.value = value;
    }

    public static BookSearchOption title(final String searchValue) {
        return BookSearchOption.builder()
                .type(BookSearchType.TITLE)
                .value(searchValue)
                .build();
    }

    public static BookSearchOption isbn(final String searchValue) {
        return BookSearchOption.builder()
                .type(BookSearchType.ISBN)
                .value(searchValue)
                .build();
    }

    public String getTarget() {
        return type.name()
                .toLowerCase();
    }
}
