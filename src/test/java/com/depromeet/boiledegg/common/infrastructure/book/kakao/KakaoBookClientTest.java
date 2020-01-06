package com.depromeet.boiledegg.common.infrastructure.book.kakao;

import com.depromeet.boiledegg.common.infrastructure.book.BookSearchFailException;
import com.depromeet.boiledegg.common.infrastructure.book.BookSearchOption;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Disabled
@RunWith(SpringRunner.class)
@SpringBootTest
class KakaoBookClientTest {

    @Autowired
    private KakaoBookClient kakaoBookClient;

    @Test
    void send() throws BookSearchFailException {
        final var response = kakaoBookClient.search(BookSearchOption.title("토이스토리"));

        System.out.println(response.getBooks());
    }
}