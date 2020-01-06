package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import com.depromeet.boiledegg.common.infrastructure.book.BookSearchFailException;

final class KakaoBookResponseParseException extends BookSearchFailException {

    public KakaoBookResponseParseException(final Throwable cause) {
        super(cause);
    }
}
