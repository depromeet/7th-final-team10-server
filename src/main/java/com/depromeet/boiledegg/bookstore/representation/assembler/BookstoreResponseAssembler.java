package com.depromeet.boiledegg.bookstore.representation.assembler;

import com.depromeet.boiledegg.bookstore.domain.entity.Bookstore;
import com.depromeet.boiledegg.bookstore.representation.BookstoreResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class BookstoreResponseAssembler {

    private final ModelMapper mapper;

    public BookstoreResponse mapFrom(final Bookstore bookstore) {
        return mapper.map(bookstore, BookstoreResponse.class);
    }
}
