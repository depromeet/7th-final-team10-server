package com.depromeet.boiledegg.account.domain.entity;

import com.depromeet.boiledegg.account.domain.AuthProvider;
import com.depromeet.boiledegg.account.domain.Role;
import com.depromeet.boiledegg.common.domain.entity.DateAuditEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email", "authProvider"}))
@Entity
public class Account extends DateAuditEntity {

    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthProvider authProvider;

    @Getter
    @Column(nullable = false)
    private String name;

    @Getter
    @Column(nullable = true)
    private String picture;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public Account(
            final String email,
            final String name,
            final AuthProvider authProvider,
            final String picture,
            final Role role
    ) {
        this.email = email;
        this.name = name;
        this.authProvider = authProvider;
        this.picture = picture;
        this.role = role;
    }

    public Account updateName(final String name) {
        this.name = name;
        return this;
    }

    public Account updatePicture(final String picture) {
        this.picture = picture;
        return this;
    }
}
