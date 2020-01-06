package com.depromeet.boiledegg.book.representation.controller;

import com.depromeet.boiledegg.book.application.service.BookService;
import com.depromeet.boiledegg.book.exception.BookNotFoundException;
import com.depromeet.boiledegg.book.representation.BookResponse;
import com.depromeet.boiledegg.book.representation.BookSaveRequest;
import com.depromeet.boiledegg.book.representation.assembler.BookResponseAssembler;
import com.depromeet.boiledegg.common.infrastructure.book.BookInformation;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchType;
import com.depromeet.boiledegg.common.infrastructure.security.LoginUser;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.common.representation.PageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(BookController.BASE_PATH)
final class BookController {

    // TODO like

    // TODO search

    // TODO 최신, 베스트(like)

    public static final String BASE_PATH = "/books";

    private final BookService bookService;

    private final BookResponseAssembler assembler;

    @PostMapping
    ResponseEntity<BookResponse> save(@RequestBody final BookSaveRequest request) {
        final var book = bookService.save(request);

        final var uri = URI.create(BASE_PATH + "/" + book.getId());
        return ResponseEntity.created(uri)
                .body(assembler.mapFrom(book));
    }

    @GetMapping
    Page<BookResponse> findAll(final PageRequest pageRequest) {
        return bookService.findAll(pageRequest)
                .map(assembler::mapFrom);
    }

    @GetMapping("/{id}")
    BookResponse findById(@PathVariable final Long id) {
        return bookService.findById(id)
                .map(assembler::mapFrom)
                .orElseThrow(BookNotFoundException::new);
    }

    @PostMapping("/{id}/like")
    BookResponse like(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        final var book = bookService.like(
                id,
                user
        );

        return assembler.mapFrom(book);
    }

    @GetMapping("/{id}/search")
    BookInformation search(@PathVariable final Long id) {
        return bookService.search(id);
    }

    @GetMapping("/search")
    List<BookInformation> search(
            @RequestParam(required = false, defaultValue = "TITLE") final BookSearchType type,
            @RequestParam final String value
    ) {
        return bookService.search(
                type,
                value
        );
    }
}
