package com.depromeet.boiledegg.user.application;

import com.depromeet.boiledegg.book.application.service.BookService;
import com.depromeet.boiledegg.book.representation.BookResponse;
import com.depromeet.boiledegg.book.representation.assembler.BookResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserBookService {

    private final BookService bookService;

    private final BookResponseAssembler assembler;

    public Page<BookResponse> findByOwner(
            final Long userId,
            final Pageable pageable
    ) {
        final var books = bookService.findByOwner(
                userId,
                pageable
        );

        return books.map(assembler::mapFrom);
    }
}
