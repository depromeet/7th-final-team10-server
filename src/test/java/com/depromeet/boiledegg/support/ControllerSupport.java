package com.depromeet.boiledegg.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@AutoConfigureRestDocs(
        uriScheme = ApiDocumentUtil.SCHEME,
        uriHost = ApiDocumentUtil.HOST,
        uriPort = ApiDocumentUtil.PORT
)
public abstract class ControllerSupport extends BaseSupport {

    @Autowired
    protected MockMvc mvc;
}
