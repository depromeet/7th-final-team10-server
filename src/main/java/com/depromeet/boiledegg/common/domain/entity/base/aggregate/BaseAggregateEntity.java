package com.depromeet.boiledegg.common.domain.entity.base.aggregate;

import org.springframework.data.domain.AbstractAggregateRoot;

abstract class BaseAggregateEntity<A extends BaseAggregateEntity<A>> extends AbstractAggregateRoot<A> {
}
