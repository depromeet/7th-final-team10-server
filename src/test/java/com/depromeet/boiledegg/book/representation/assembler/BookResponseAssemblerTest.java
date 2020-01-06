package com.depromeet.boiledegg.book.representation.assembler;

import com.depromeet.boiledegg.book.domain.entity.Book;
import com.depromeet.boiledegg.book.domain.repository.BookRepository;
import com.depromeet.boiledegg.support.BaseSupport;
import com.depromeet.boiledegg.support.MockSessionUserHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class BookResponseAssemblerTest extends BaseSupport {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookResponseAssembler assembler;

    @Test
    void mapFrom() {
        final var book = Book.builder()
                .isbn("123456")
                .build();

        repository.save(book);
        book.like(MockSessionUserHolder.getMockSessionUser());

        final var response = assembler.mapFrom(book);

        assertThat(book.getIsbn()).isEqualTo(response.getIsbn());
        assertThat(book.getLikeCountValue()).isEqualTo(response.getLikeCount());
        assertThat(book.getOwner()).isEqualTo(response.getOwner());
    }
}