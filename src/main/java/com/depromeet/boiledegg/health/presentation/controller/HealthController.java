package com.depromeet.boiledegg.health.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class HealthController {

    @GetMapping("/health")
    String healthCheck() {
        return "OK";
    }
}
