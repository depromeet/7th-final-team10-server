package com.depromeet.boiledegg.system.presentation.controller;

import com.depromeet.boiledegg.common.infrastructure.security.LoginUser;
import com.depromeet.boiledegg.common.infrastructure.security.SessionUser;
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

    @GetMapping("/me")
    public SessionUser me(@LoginUser final SessionUser user) {
        return user;
    }
}
