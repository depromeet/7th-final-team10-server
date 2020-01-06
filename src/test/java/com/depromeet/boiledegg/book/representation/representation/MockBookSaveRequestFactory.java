package com.depromeet.boiledegg.book.representation.representation;

import com.depromeet.boiledegg.book.representation.BookSaveRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MockBookSaveRequestFactory {

    public BookSaveRequest create() {
        return BookSaveRequest.builder()
                .isbn("9788964150290")
                .build();
    }
}
