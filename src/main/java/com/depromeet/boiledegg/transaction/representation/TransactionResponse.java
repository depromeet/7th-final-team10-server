package com.depromeet.boiledegg.transaction.representation;

import com.depromeet.boiledegg.book.representation.BookResponse;
import com.depromeet.boiledegg.transaction.domain.TransactionStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionResponse {

    private Long id;

    private Long owner;

    private BookResponse book;

    private TransactionStatus status;
}
