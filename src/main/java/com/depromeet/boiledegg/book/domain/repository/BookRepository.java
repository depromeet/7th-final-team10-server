package com.depromeet.boiledegg.book.domain.repository;

import com.depromeet.boiledegg.book.domain.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByOwner(
            final long owner,
            final Pageable pageable
    );
}
