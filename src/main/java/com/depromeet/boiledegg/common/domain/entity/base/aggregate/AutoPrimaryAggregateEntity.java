package com.depromeet.boiledegg.common.domain.entity.base.aggregate;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AutoPrimaryAggregateEntity<A extends BaseAggregateEntity<A>> extends BaseAggregateEntity<A> {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
