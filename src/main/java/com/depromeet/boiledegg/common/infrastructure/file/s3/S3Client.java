package com.depromeet.boiledegg.common.infrastructure.file.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.depromeet.boiledegg.common.utils.random.RandomString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Component
final class S3Client {

    // TODO 어디로 갈까?
    // TODO Assembler
    // TODO Error handling

    private static final String FILENAME_HEADER_NAME = "filename";
    private static final int RANDOM_FILE_NAME_SALT_LENGTH = 10;

    private final AmazonS3 client;

    private final String defaultBucket;

    public S3Client(final S3Properties properties) {
        final var awsProperties = properties.getAws();

        final var credentials = new BasicAWSCredentials(
                awsProperties.getAccessKey(),
                awsProperties.getSecretKey()
        );

        final var staticCredentialsProvider = new AWSStaticCredentialsProvider(credentials);

        client = AmazonS3Client.builder()
                .withCredentials(staticCredentialsProvider)
                .withRegion(awsProperties.getRegion())
                .build();

        defaultBucket = properties.getBucketName();
    }

    public void upload(final MultipartFile file) {
        final var metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setHeader(FILENAME_HEADER_NAME, file.getOriginalFilename());

        try {
            final var request = new PutObjectRequest(
                    defaultBucket,
                    generateRandomFileName(),
                    file.getInputStream(),
                    metadata
            );
            var result = client.putObject(request);


            log.info("@@ result={}", result);
        } catch (final AmazonServiceException e) {
            // aws error
        } catch (final SdkClientException e) {
            // client error
        } catch (final IOException e) {
            // io error
        }
    }

    private String generateRandomFileName() {
        return RandomString.getTimebaseUuid() + RandomString.getSecureString(RANDOM_FILE_NAME_SALT_LENGTH);
    }
}
