package com.depromeet.boiledegg.common.infrastructure.email.ses;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.AccountSendingPausedException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetDoesNotExistException;
import com.amazonaws.services.simpleemail.model.ConfigurationSetSendingPausedException;
import com.amazonaws.services.simpleemail.model.MailFromDomainNotVerifiedException;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.depromeet.boiledegg.common.infrastructure.email.EmailClient;
import com.depromeet.boiledegg.common.infrastructure.email.EmailMessageId;
import com.depromeet.boiledegg.common.infrastructure.email.EmailSendFailException;
import com.depromeet.boiledegg.common.infrastructure.email.MailItem;
import com.depromeet.boiledegg.common.infrastructure.email.SendEmailRequestAssembler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public final class SesClient implements EmailClient {

    private final SendEmailRequestAssembler assembler;

    private final AmazonSimpleEmailService client;

    public SesClient(
            final SendEmailRequestAssembler assembler,
            final SesProperties properties
    ) {
        this.assembler = assembler;

        final var awsProperties = properties.getAws();

        final var credentials = new BasicAWSCredentials(
                awsProperties.getAccessKey(),
                awsProperties.getSecretKey()
        );

        final var staticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);

        client = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(staticCredentialsProvider)
                .withRegion(awsProperties.getRegion())
                .build();
    }

    // TODO 에러 처리 메시지 자세히
    @Override
    public EmailMessageId<String> send(final MailItem mailItem) throws EmailSendFailException {
        final var sendEmailRequest = assembler.mapFrom(mailItem);
        try {
            final var result = client.sendEmail(sendEmailRequest);

            return result::getMessageId;
        } catch (final MessageRejectedException e) {
            log.error("Ses Client mail send reject ", e);
            throw new EmailSendFailException(e);
        } catch (final MailFromDomainNotVerifiedException | AccountSendingPausedException |
                ConfigurationSetSendingPausedException | ConfigurationSetDoesNotExistException e) {
            log.error("Ses Client mail send error ", e);
            throw new EmailSendFailException(e);
        }
    }
}
