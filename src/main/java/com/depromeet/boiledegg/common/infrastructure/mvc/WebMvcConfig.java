package com.depromeet.boiledegg.common.infrastructure.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        final var mappingPathPattern = "/**";
        final var allowedOrigins = "*";
        final var allowedMethods = Arrays.stream(HttpMethod.values())
                .map(Enum::name)
                .toArray(String[]::new);

        registry.addMapping(mappingPathPattern)
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .allowCredentials(false)
                .maxAge(3600);
    }
}
