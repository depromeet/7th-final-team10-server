package com.depromeet.boiledegg.user.domain.entity;

import com.depromeet.boiledegg.common.domain.entity.base.DateAuditEntity;
import com.depromeet.boiledegg.user.domain.AuthProvider;
import com.depromeet.boiledegg.user.domain.Role;
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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "authProvider"})
})
@Entity
public class User extends DateAuditEntity {

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
    private String nickname;

    @Getter
    @Column(nullable = true)
    private String picture;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(
            final String email,
            final String name,
            final String nickname,
            final AuthProvider authProvider,
            final String picture,
            final Role role
    ) {
        this.email = email;
        this.name = name;
        this.nickname = nickname;
        this.authProvider = authProvider;
        this.picture = picture;
        this.role = role;
    }

    public User update(final User user) {
        return updateName(user.name)
                .updatePicture(user.picture)
                .updateNickname(user.nickname);
    }

    public User updateName(final String name) {
        this.name = name;
        return this;
    }

    public User updatePicture(final String picture) {
        this.picture = picture;
        return this;
    }

    public User updateNickname(final String nickname) {
        this.nickname = nickname;
        return this;
    }
}
