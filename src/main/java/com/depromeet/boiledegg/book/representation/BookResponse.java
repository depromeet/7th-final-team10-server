package com.depromeet.boiledegg.book.representation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class BookResponse {

    private Long id;

    private Long owner;

    private String isbn;

    private int likeCount;
}
