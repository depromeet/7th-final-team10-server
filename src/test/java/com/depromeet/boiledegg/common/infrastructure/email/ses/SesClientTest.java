package com.depromeet.boiledegg.common.infrastructure.email.ses;

import com.depromeet.boiledegg.common.infrastructure.email.EmailSendFailException;
import com.depromeet.boiledegg.common.infrastructure.email.MailItem;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Disabled
@RunWith(SpringRunner.class)
@SpringBootTest
class SesClientTest {

    @Autowired
    private SesClient sesClient;

    @Test
    void send() throws EmailSendFailException {
        sesClient.send(MailItem.builder()
                .content("Hello!!")
                .subject("Title")
                .to("jaeyeonling@gmail.com")
                .to("fanast1@naver.com")
                .build());
    }
}