package com.depromeet.boiledegg.common.properties;

import lombok.Data;

@Data
public final class AwsProperties {

    private String region;

    private String accessKey;

    private String secretKey;
}
