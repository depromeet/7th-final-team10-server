package com.depromeet.boiledegg.notfound.presentation;

import com.depromeet.boiledegg.notfound.exception.NotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotFoundController {

    @RequestMapping
    void notFound() {
        throw new NotFoundException();
    }
}
