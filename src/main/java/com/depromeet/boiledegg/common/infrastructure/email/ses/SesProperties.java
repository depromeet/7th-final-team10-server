package com.depromeet.boiledegg.common.infrastructure.email.ses;

import com.depromeet.boiledegg.common.properties.AwsProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "boiled-egg.email.ses")
final class SesProperties {

    private AwsProperties aws;
}
