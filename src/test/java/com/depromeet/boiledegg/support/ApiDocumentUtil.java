package com.depromeet.boiledegg.support;

import lombok.experimental.UtilityClass;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.modifyUris;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.snippet.Attributes.key;

@UtilityClass
public class ApiDocumentUtil {
    public static final String SCHEME = "http";
    public static final String HOST = "boiled-egg-api.jaeyeonling.com";
    public static final int PORT = 8080;

    public OperationRequestPreprocessor request() {
        return preprocessRequest(
                modifyUris().scheme(SCHEME)
                        .host(HOST)
                        .port(PORT),
                prettyPrint()
        );
    }

    public OperationResponsePreprocessor response() {
        return preprocessResponse(prettyPrint());
    }

    public Attributes.Attribute dateFormat() {
        return key("format").value("yyyy-MM-dd");
    }
}
