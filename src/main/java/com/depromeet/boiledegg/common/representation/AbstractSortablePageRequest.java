package com.depromeet.boiledegg.common.representation;

import com.depromeet.boiledegg.book.exception.DisallowSortCriteriaException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.NonNull;

public abstract class AbstractSortablePageRequest implements Pageable {

    private final PageRequest pageRequest;

    public AbstractSortablePageRequest() {
        this(new PageRequest());
    }

    public AbstractSortablePageRequest(final PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    protected abstract boolean isDisallow(final String sortCriteria);

    public void setPage(final int page) {
        pageRequest.setPage(page);
    }

    public void setSize(final int size) {
        pageRequest.setSize(size);
    }

    public void setDirection(final Sort.Direction direction) {
        pageRequest.setDirection(direction);
    }

    public void setSortCriteria(final String sortCriteria) {
        verifySortCriteria(sortCriteria);
        pageRequest.setSortCriteria(sortCriteria);
    }

    private void verifySortCriteria(final String sortCriteria) {
        if (isDisallow(sortCriteria)) {
            throw new DisallowSortCriteriaException();
        }
    }

    public String getSortCriteria() {
        return pageRequest.getSortCriteria();
    }

    @Override
    public int getPageNumber() {
        return pageRequest.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return pageRequest.getPageSize();
    }

    @Override
    public long getOffset() {
        return pageRequest.getOffset();
    }

    @NonNull
    @Override
    public Sort getSort() {
        return pageRequest.getSort();
    }

    @NonNull
    @Override
    public Pageable next() {
        return pageRequest.next();
    }

    @NonNull
    @Override
    public Pageable previousOrFirst() {
        return pageRequest.previousOrFirst();
    }

    @NonNull
    @Override
    public Pageable first() {
        return pageRequest.first();
    }

    @Override
    public boolean hasPrevious() {
        return pageRequest.hasPrevious();
    }
}
