package com.depromeet.boiledegg.common.infrastructure.file.s3;

import com.depromeet.boiledegg.common.properties.AwsProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "boiled-egg.file.s3")
final class S3Properties {

    private String bucketName;

    private AwsProperties aws;
}
