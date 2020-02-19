package com.depromeet.boiledegg.book.representation;

import com.depromeet.boiledegg.common.representation.AbstractSortablePageRequest;
import com.depromeet.boiledegg.common.representation.PageRequest;

import java.util.Collection;
import java.util.Set;

public final class BookPageRequest extends AbstractSortablePageRequest {

    private static final Collection<String> ALLOW_SORT_CRITERIA = Set.of("id", "likeCount");

    public BookPageRequest() {
        super();
    }

    public BookPageRequest(final PageRequest pageRequest) {
        super(pageRequest);
    }

    @Override
    protected boolean isDisallow(final String sortCriteria) {
        return !ALLOW_SORT_CRITERIA.contains(sortCriteria);
    }
}
