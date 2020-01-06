package com.depromeet.boiledegg.book.domain.repository;

import com.depromeet.boiledegg.book.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
