package com.depromeet.boiledegg.common.infrastructure.email;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "boiled-egg.email")
final class EmailProperties {

    private String contentCharSet;

    private String sender;
}
