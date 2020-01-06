package com.depromeet.boiledegg.post.representation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class PostSaveRequest {

    private String title;

    private String content;

    @Builder
    public PostSaveRequest(
            final String title,
            final String content
    ) {
        this.title = title;
        this.content = content;
    }
}
