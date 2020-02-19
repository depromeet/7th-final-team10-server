package com.depromeet.boiledegg.security.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

    @Secured("ROLE_ADMIN")
    @GetMapping("/only-admin")
    ResponseEntity<String> onlyAdmin() {
        return ResponseEntity.ok().build();
    }

    @Secured("ROLE_USER")
    @GetMapping("/only-user")
    ResponseEntity<String> onlyUser() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/public")
    ResponseEntity<String> publik() {
        return ResponseEntity.ok().build();
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/normal")
    ResponseEntity<String> normal() {
        return ResponseEntity.ok().build();
    }
}
