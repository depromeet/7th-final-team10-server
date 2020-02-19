package com.depromeet.boiledegg.book.representation.representation;

import com.depromeet.boiledegg.book.domain.BookCategory;
import com.depromeet.boiledegg.book.representation.BookSaveRequest;
import lombok.experimental.UtilityClass;

import java.util.Set;

@UtilityClass
public class MockBookSaveRequestFactory {

    public BookSaveRequest create() {
        return BookSaveRequest.builder()
                .isbn("9788964150290")
                .bookCategories(Set.of(BookCategory.values()))
                .build();
    }
}
