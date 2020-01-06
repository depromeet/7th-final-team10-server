package com.depromeet.boiledegg.common.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Coordinate {

    @Getter
    @Column(nullable = false)
    private Double latitude;

    @Getter
    @Column(nullable = false)
    private Double longitude;

    @Builder
    public Coordinate(
            final Double latitude,
            final Double longitude
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
