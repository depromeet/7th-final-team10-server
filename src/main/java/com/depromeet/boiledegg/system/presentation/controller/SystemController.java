package com.depromeet.boiledegg.system.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SystemController {

    @GetMapping("/current-time-millis")
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
