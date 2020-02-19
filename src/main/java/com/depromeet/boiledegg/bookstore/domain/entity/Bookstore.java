package com.depromeet.boiledegg.bookstore.domain.entity;

import com.depromeet.boiledegg.common.domain.LikeCount;
import com.depromeet.boiledegg.common.domain.Location;
import com.depromeet.boiledegg.common.domain.entity.base.CreateByAuditEntity;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table // TODO (uniqueConstraints = @UniqueConstraint(columnNames = {"owner", "name"}))
@Entity
public class Bookstore extends CreateByAuditEntity {

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Column(nullable = true)
    private String description;

    @Getter
    @Embedded
    private Location location;

    @Getter
    @Embedded
    private LikeCount likeCount;

    @Builder
    public Bookstore(
            final String name,
            final String description,
            final Location location
    ) {
        this.name = name;
        this.description = description;
        this.location = location;
        likeCount = LikeCount.getDefault();
    }

    public Bookstore like(final SessionUser user) {
        // TODO 중복 추천 방지, 내 책방 추천 방지

        likeCount = likeCount.increment();
        return this;
    }
}
