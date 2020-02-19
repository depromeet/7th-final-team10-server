package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import org.springframework.stereotype.Component;

@Component
final class KakaoBookAssembler {

    public BookInformation mapFrom(final KakaoBookResponse.Document document) {
        return KakaoBook.builder()
                .title(document.getTitle())
                .contents(document.getContents())
                .authors(document.getAuthors())
                .translators(document.getTranslators())
                .isbn(document.getIsbn())
                .price(document.getPrice())
                .publisher(document.getPublisher())
                .thumbnail(document.getThumbnail())
                .datetime(document.getDatetime())
                .build();
    }
}
