package com.depromeet.boiledegg.common.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
@Embeddable
public class Location {

    @Getter
    @Embedded
    private Coordinate coordinate;

    @Getter
    @Column(length = 500, nullable = true)
    private String detail;

    @Builder
    public Location(
            final Coordinate coordinate,
            final String detail
    ) {
        this.coordinate = coordinate;
        this.detail = detail;
    }
}
