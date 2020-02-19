package com.depromeet.boiledegg.transaction.infrastructure;

import com.depromeet.boiledegg.book.application.Bookshelf;
import com.depromeet.boiledegg.common.infrastructure.email.EmailClient;
import com.depromeet.boiledegg.common.infrastructure.email.EmailSendFailException;
import com.depromeet.boiledegg.common.infrastructure.email.MailItem;
import com.depromeet.boiledegg.transaction.application.StatusChangeNotifyer;
import com.depromeet.boiledegg.transaction.domain.event.StatusChangeEvent;
import com.depromeet.boiledegg.user.application.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Profile("prod")
@AllArgsConstructor
@Component
class EmailStatusChangeNotifyer implements StatusChangeNotifyer {

    private final EmailClient emailClient;

    private final Bookshelf bookshelf;

    private final UserService userService;

    // TODO async?
    @TransactionalEventListener
    @Override
    public void notify(final StatusChangeEvent event) {
        final var borrower = userService.findOrThrow(event.getBorrower());
        final var bookOwner = userService.findOrThrow(event.getBookOwner());
        final var book = bookshelf.findByIsbn(event.getIsbn());

        // TODO 메시지 제목 / 내용 정해지면 수정
        final var subject = "제목";
        final var content = "내용";

        sendEmail(MailItem.builder()
                .to(borrower.getEmail())
                .to(bookOwner.getEmail())
                .content(content)
                .subject(subject)
                .build());
    }

    private void sendEmail(final MailItem mailItem) {
        try {
            emailClient.send(mailItem);
        } catch (final EmailSendFailException e) {
            e.printStackTrace();
        }
    }
}
