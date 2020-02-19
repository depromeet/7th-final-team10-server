package com.depromeet.boiledegg.book.domain;

import com.depromeet.boiledegg.book.exception.BookAlreadyBorrowException;
import com.depromeet.boiledegg.book.exception.BookAlreadyWaitException;

public enum BookStatus {

    WAIT,
    BORROWED;

    public boolean isWait() {
        return this == WAIT;
    }

    public boolean isBorrowed() {
        return this == BORROWED;
    }

    public BookStatus borrow() {
        verifyAlreadyBorrow();
        return BORROWED;
    }

    public BookStatus returns() {
        verifyAlreadyWait();
        return BORROWED;
    }

    public BookStatus cancel() {
        verifyAlreadyWait();
        return WAIT;
    }

    public BookStatus take() {
        verifyAlreadyWait();
        return WAIT;
    }

    public BookStatus reject() {
        return WAIT;
    }

    public void verifyAlreadyBorrow() {
        if (isBorrowed()) {
            throw new BookAlreadyBorrowException();
        }
    }

    public void verifyAlreadyWait() {
        if (isWait()) {
            throw new BookAlreadyWaitException();
        }
    }
}
