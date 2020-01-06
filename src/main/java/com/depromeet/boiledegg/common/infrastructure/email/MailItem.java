package com.depromeet.boiledegg.common.infrastructure.email;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MailItem {

    private final List<String> to = new ArrayList<>();

    private final String subject;

    private final String content;

    @Builder
    public MailItem(
            @Singular("to")
            final List<String> to,
            final String subject,
            final String content
    ) {
        this.to.addAll(to);
        this.subject = subject;
        this.content = content;
    }
}
