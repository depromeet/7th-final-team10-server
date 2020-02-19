package com.depromeet.boiledegg.common.infrastructure.file.s3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
class S3ClientTest {

    @Autowired
    private S3Client s3Client;

    @Disabled
    @Test
    void upload() throws IOException {
        final var mockMultipartFile = new MockMultipartFile(
                "/Users/jaeyeonling/Desktop/82513681959457430_57164302.jpg",
                "82513681959457430_57164302.jpg",
                "image/jpeg",
                new FileInputStream(new File("/Users/jaeyeonling/Desktop/82513681959457430_57164302.jpg"))
        );

        s3Client.upload(mockMultipartFile);
    }
}