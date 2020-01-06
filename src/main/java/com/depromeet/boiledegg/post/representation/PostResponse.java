package com.depromeet.boiledegg.post.representation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public final class PostResponse {

    private Long id;

    private String title;

    private String content;
}
