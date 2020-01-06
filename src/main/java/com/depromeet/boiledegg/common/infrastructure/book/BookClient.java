package com.depromeet.boiledegg.common.infrastructure.book;

@FunctionalInterface
public interface BookClient {

    BookSearchResponse search(final BookSearchOption bookSearchOption) throws BookSearchFailException;
}
