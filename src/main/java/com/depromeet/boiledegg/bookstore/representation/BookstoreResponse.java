package com.depromeet.boiledegg.bookstore.representation;

import com.depromeet.boiledegg.common.domain.Location;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookstoreResponse {

    private Long id;

    private Long owner;

    private String name;

    private String description;

    private Location location;

    private int likeCount;
}
