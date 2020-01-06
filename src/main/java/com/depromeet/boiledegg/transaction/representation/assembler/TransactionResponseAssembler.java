package com.depromeet.boiledegg.transaction.representation.assembler;

import com.depromeet.boiledegg.transaction.domain.entity.Transaction;
import com.depromeet.boiledegg.transaction.representation.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public final class TransactionResponseAssembler {

    private final ModelMapper mapper;

    public TransactionResponse mapFrom(final Transaction transaction) {
        return mapper.map(transaction, TransactionResponse.class);
    }
}
