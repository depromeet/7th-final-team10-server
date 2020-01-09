package com.depromeet.boiledegg.bookstore.representation.controller;

import com.depromeet.boiledegg.bookstore.application.service.BookstoreService;
import com.depromeet.boiledegg.bookstore.exception.BookstoreNotFoundException;
import com.depromeet.boiledegg.bookstore.representation.BookstoreResponse;
import com.depromeet.boiledegg.bookstore.representation.BookstoreSaveRequest;
import com.depromeet.boiledegg.bookstore.representation.assembler.BookstoreResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(BookstoreController.BASE_PATH)
public class BookstoreController {

    // TODO like

    // TODO search

    // TODO 최신, 베스트(like)

    static final String BASE_PATH = "/bookstores";

    private final BookstoreService bookstoreService;

    private final BookstoreResponseAssembler assembler;

    @PostMapping
    ResponseEntity<BookstoreResponse> save(@RequestBody final BookstoreSaveRequest request) {
        final var bookstore = bookstoreService.save(request);

        final var uri = URI.create(BASE_PATH + "/" + bookstore.getId());
        return ResponseEntity.created(uri)
                .body(assembler.mapFrom(bookstore));
    }

    @GetMapping
    Page<BookstoreResponse> findAll(final Pageable pageable) {
        return bookstoreService.findAll(pageable)
                .map(assembler::mapFrom);
    }

    @GetMapping("/{id}")
    BookstoreResponse findById(@PathVariable final Long id) {
        return bookstoreService.findById(id)
                .map(assembler::mapFrom)
                .orElseThrow(BookstoreNotFoundException::new);
    }
}
