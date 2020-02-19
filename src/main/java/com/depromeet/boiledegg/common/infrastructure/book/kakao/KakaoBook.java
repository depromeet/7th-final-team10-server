package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import com.depromeet.boiledegg.common.utils.DateUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;

@Slf4j
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

    private Date publishedAt;

    @Builder
    public KakaoBook(
            final String title,
            final String contents,
            final List<String> authors,
            final List<String> translators,
            final String isbn,
            final int price,
            final String publisher,
            final String thumbnail,
            final String datetime
    ) {
        this.title = title;
        this.contents = contents;
        this.authors = authors;
        this.translators = translators;
        this.isbn = isbn;
        this.price = price;
        this.publisher = publisher;
        this.thumbnail = thumbnail;

        DateUtils.parse(datetime).ifPresent(date -> publishedAt = date);
    }
}
