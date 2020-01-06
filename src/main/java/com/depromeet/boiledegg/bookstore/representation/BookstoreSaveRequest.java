package com.depromeet.boiledegg.bookstore.representation;

import com.depromeet.boiledegg.common.domain.Location;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class BookstoreSaveRequest {

    private String name;

    private String description;

    private Location location;

    @Builder
    public BookstoreSaveRequest(
            final String name,
            final String description,
            final Location location
    ) {
        this.name = name;
        this.description = description;
        this.location = location;
    }
}
