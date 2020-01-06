package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Data
final class KakaoBook implements BookInformation {

    private String title;

    private String contents;

    private List<String> authors;

    private List<String> translators;

    private String isbn;

    private int price;

    private String publisher;

    private String thumbnail;

    @Builder
    public KakaoBook(
            final String title,
            final String contents,
            final List<String> authors,
            final List<String> translators,
            final String isbn,
            final int price,
            final String publisher,
            final String thumbnail
    ) {
        this.title = title;
        this.contents = contents;
        this.authors = authors;
        this.translators = translators;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;
    }
}
