package com.depromeet.boiledegg.security.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
final class SecurityController {

    @GetMapping("/only-admin")
    ResponseEntity<String> onlyAdmin() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/only-user")
    ResponseEntity<String> onlyUser() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public")
    ResponseEntity<String> publik() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/normal")
    ResponseEntity<String> normal() {
        return ResponseEntity.ok().build();
    }
}
