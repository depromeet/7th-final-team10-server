package com.depromeet.boiledegg.transaction.representation.controller;

import com.depromeet.boiledegg.common.infrastructure.security.LoginUser;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import com.depromeet.boiledegg.common.representation.PageRequest;
import com.depromeet.boiledegg.transaction.application.service.TransactionService;
import com.depromeet.boiledegg.transaction.exception.TransactionNotFoundException;
import com.depromeet.boiledegg.transaction.representation.TransactionResponse;
import com.depromeet.boiledegg.transaction.representation.TransactionSaveRequest;
import com.depromeet.boiledegg.transaction.representation.assembler.TransactionResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping(TransactionController.BASE_PATH)
public class TransactionController {

    static final String BASE_PATH = "/transactions";

    private final TransactionService transactionService;

    private final TransactionResponseAssembler assembler;

    @PostMapping
    ResponseEntity<TransactionResponse> save(
            @LoginUser final SessionUser user,
            @RequestBody final TransactionSaveRequest request
    ) {
        final var transaction = transactionService.save(
                user,
                request
        );

        final var uri = URI.create(BASE_PATH + "/" + transaction.getId());
        return ResponseEntity.created(uri)
                .body(assembler.mapFrom(transaction));
    }

    @GetMapping
    Page<TransactionResponse> findAll(final PageRequest pageRequest) {
        return transactionService.findAll(pageRequest)
                .map(assembler::mapFrom);
    }

    @GetMapping("/{id}")
    TransactionResponse findById(@PathVariable final Long id) {
        return transactionService.findById(id)
                .map(assembler::mapFrom)
                .orElseThrow(TransactionNotFoundException::new);
    }

    @PutMapping("/{id}/confirm")
    TransactionResponse confirm(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        final var transaction = transactionService.confirm(
                id,
                user
        );

        return assembler.mapFrom(transaction);
    }

    @PutMapping("/{id}/reject")
    TransactionResponse reject(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        final var transaction = transactionService.reject(
                id,
                user
        );

        return assembler.mapFrom(transaction);
    }

    @PutMapping("/{id}/cancel")
    TransactionResponse cancel(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        final var transaction = transactionService.cancel(
                id,
                user
        );

        return assembler.mapFrom(transaction);
    }

    @PutMapping("/{id}/return")
    TransactionResponse returns(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        final var transaction = transactionService.returns(
                id,
                user
        );

        return assembler.mapFrom(transaction);
    }

    @PutMapping("/{id}/take")
    TransactionResponse take(
            @PathVariable final Long id,
            @LoginUser final SessionUser user
    ) {
        final var transaction = transactionService.take(
                id,
                user
        );

        return assembler.mapFrom(transaction);
    }
}
