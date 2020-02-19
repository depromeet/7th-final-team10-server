package com.depromeet.boiledegg.bookstore.representation;

import com.depromeet.boiledegg.common.representation.AbstractSortablePageRequest;
import com.depromeet.boiledegg.common.representation.PageRequest;

import java.util.Collection;
import java.util.Set;

public final class BookstorePageRequest extends AbstractSortablePageRequest {

    private static final Collection<String> ALLOW_SORT_CRITERIA = Set.of("id", "likeCount");

    public BookstorePageRequest() {
        super();
    }

    public BookstorePageRequest(final PageRequest pageRequest) {
        super(pageRequest);
    }

    @Override
    protected boolean isDisallow(final String sortCriteria) {
        return !ALLOW_SORT_CRITERIA.contains(sortCriteria);
    }
}
