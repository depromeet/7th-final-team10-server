package com.depromeet.boiledegg.common.infrastructure.book;

import java.util.List;

@FunctionalInterface
public interface BookSearchResponse {

    List<BookInformation> getBooks();
}
