package com.depromeet.boiledegg.common.domain.entity.base.aggregate;

import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAuditAggregateEntity<A extends BaseAggregateEntity<A>>
        extends CreateDateAuditAggregateEntity<A> {

    @Getter
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime lastModifiedDate;
}
