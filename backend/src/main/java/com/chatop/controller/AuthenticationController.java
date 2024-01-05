package com.chatop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.model.Authentication;
import com.chatop.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private AuthenticationService AuthenticationService;

    @PostMapping("/register")
    public ResponseEntity<Authentication> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(AuthenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Authentication> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(AuthenticationService.login(request));
    }
}
