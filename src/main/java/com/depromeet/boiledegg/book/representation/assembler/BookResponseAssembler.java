package com.depromeet.boiledegg.book.representation.assembler;

import com.depromeet.boiledegg.book.domain.entity.Book;
import com.depromeet.boiledegg.book.representation.BookResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class BookResponseAssembler {

    private final ModelMapper mapper;

    public BookResponse mapFrom(final Book book) {
        return mapper.map(book, BookResponse.class);
    }
}
