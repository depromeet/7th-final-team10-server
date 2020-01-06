package com.depromeet.boiledegg.common.domain.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreateDateAuditEntity extends AutoPrimaryEntity {

    @Getter
    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;
}
