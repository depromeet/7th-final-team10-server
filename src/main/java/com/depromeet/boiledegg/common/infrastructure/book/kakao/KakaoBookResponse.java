package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KakaoBookResponse {

    private List<Document> documents;

    private Meta meta;

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Document {

        private List<String> authors;

        private String contents;

        private String datetime;

        private String isbn;

        private int price;

        private String publisher;

        private int salePrice;

        private String status;

        private String thumbnail;

        private String title;

        private List<String> translators;

        private String url;
    }

    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Meta {

        private boolean end;

        private int pageableCount;

        private int totalCount;
    }
}
