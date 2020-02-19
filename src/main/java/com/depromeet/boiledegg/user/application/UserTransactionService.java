package com.depromeet.boiledegg.user.application;

import com.depromeet.boiledegg.transaction.application.service.TransactionService;
import com.depromeet.boiledegg.transaction.representation.TransactionResponse;
import com.depromeet.boiledegg.transaction.representation.assembler.TransactionResponseAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserTransactionService {

    private final TransactionService transactionService;

    private final TransactionResponseAssembler assembler;

    public Page<TransactionResponse> findByBookOwnerOrOwner(
            final Long userId,
            final Pageable pageable
    ) {
        final var transactions = transactionService.findByBookOwnerOrOwner(
                userId,
                pageable
        );

        return transactions.map(assembler::mapFrom);
    }
}
