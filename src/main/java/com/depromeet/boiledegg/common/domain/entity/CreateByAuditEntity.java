package com.depromeet.boiledegg.common.domain.entity;

import com.depromeet.boiledegg.common.exception.ForbiddenException;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@Slf4j
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateByAuditEntity extends DateAuditEntity {

    @Getter
    @CreatedBy
    @Column(nullable = false)
    private Long owner;

    public void verifyMatchOwner(final SessionUser user) {
        if (mismatchOwner(user)) {
            log.debug("Owner match verification fail [entity={}, user={}]", this, user);
            throw new ForbiddenException();
        }
    }

    public boolean matchOwner(final SessionUser user) {
        return user.matchUserId(owner);
    }

    public boolean mismatchOwner(final SessionUser user) {
        return user.mismatchUserId(owner);
    }
}
