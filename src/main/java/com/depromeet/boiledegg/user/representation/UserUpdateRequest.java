package com.depromeet.boiledegg.user.representation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class UserUpdateRequest {

    private String name;

    private String picture;

    private String nickname;

    @Builder
    public UserUpdateRequest(
            final String name,
            final String picture,
            final String nickname
    ) {
        this.name = name;
        this.picture = picture;
        this.nickname = nickname;
    }
}
