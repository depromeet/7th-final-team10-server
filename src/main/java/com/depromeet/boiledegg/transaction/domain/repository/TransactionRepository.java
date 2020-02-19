package com.depromeet.boiledegg.transaction.domain.repository;

import com.depromeet.boiledegg.transaction.domain.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByBookOwnerOrOwner(
            final long bookOwner,
            final long owner,
            final Pageable pageable
    );
}
