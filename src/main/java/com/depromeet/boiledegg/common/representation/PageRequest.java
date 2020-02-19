package com.depromeet.boiledegg.common.representation;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static java.lang.Math.max;
import static java.lang.Math.min;

public final class PageRequest implements Pageable {

    private static final String DEFAULT_SORT_CRITERIA = "id";
    private static final int MIN_PAGE_NUMBER = 0;
    private static final int MAX_PAGE_SIZE = 20;

    private Pageable pageable;

    @Getter
    private String sortCriteria = DEFAULT_SORT_CRITERIA;

    public PageRequest() {
        this(org.springframework.data.domain.PageRequest.of(
                MIN_PAGE_NUMBER,
                MAX_PAGE_SIZE,
                Sort.Direction.DESC,
                DEFAULT_SORT_CRITERIA
        ));
    }

    public PageRequest(final Pageable pageable) {
        this.pageable = pageable;
    }

    public void setPage(final int page) {
        pageable = org.springframework.data.domain.PageRequest.of(
                max(MIN_PAGE_NUMBER, page),
                getPageSize(),
                getSort()
        );
    }

    public void setSize(final int size) {
        pageable = org.springframework.data.domain.PageRequest.of(
                getPageNumber(),
                min(MAX_PAGE_SIZE, size),
                pageable.getSort()
        );
    }

    public void setDirection(final Sort.Direction direction) {
        pageable = org.springframework.data.domain.PageRequest.of(
                getPageNumber(),
                getPageSize(),
                direction,
                sortCriteria
        );
    }

    public void setSortCriteria(final String sortCriteria) {
        this.sortCriteria = sortCriteria;

        pageable = org.springframework.data.domain.PageRequest.of(
                getPageNumber(),
                getPageSize(),
                getDirection(),
                sortCriteria
        );
    }

    private Sort.Direction getDirection() {
        return pageable.getSort()
                .get()
                .findFirst()
                .map(Sort.Order::getDirection)
                .orElse(Sort.Direction.ASC);
    }

    @Override
    public int getPageNumber() {
        return pageable.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return pageable.getPageSize();
    }

    @Override
    public long getOffset() {
        return pageable.getOffset();
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }

    @Override
    public Pageable next() {
        return pageable.next();
    }

    @Override
    public Pageable previousOrFirst() {
        return pageable.previousOrFirst();
    }

    @Override
    public Pageable first() {
        return pageable.first();
    }

    @Override
    public boolean hasPrevious() {
        return pageable.hasPrevious();
    }
}
