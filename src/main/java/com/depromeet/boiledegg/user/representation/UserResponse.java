package com.depromeet.boiledegg.user.representation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponse {

    private Long id;

    private String name;

    private String email;

    private String picture;

    private String nickname;
}
