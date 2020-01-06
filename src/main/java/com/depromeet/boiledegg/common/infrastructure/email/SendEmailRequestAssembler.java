package com.depromeet.boiledegg.common.infrastructure.email;

import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.springframework.stereotype.Component;

@Component
public final class SendEmailRequestAssembler {

    private final String contentCharSet;

    private final String sender;

    public SendEmailRequestAssembler(final EmailProperties emailProperties) {
        this.contentCharSet = emailProperties.getContentCharSet();
        this.sender = emailProperties.getSender();
    }

    public SendEmailRequest mapFrom(final MailItem mailItem) {
        final var destination = new Destination()
                .withToAddresses(mailItem.getTo());

        final var subject = createContent(mailItem.getSubject());
        final var contents = createContent(mailItem.getContent());
        final var body = new Body().withText(contents);
        final var message = new Message()
                .withSubject(subject)
                .withBody(body);

        return new SendEmailRequest()
                .withSource(sender)
                .withDestination(destination)
                .withMessage(message);
    }

    private Content createContent(final String text) {
        return new Content()
                .withCharset(contentCharSet)
                .withData(text);
    }
}
