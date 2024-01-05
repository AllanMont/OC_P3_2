package com.chatop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.RegisterRequest;
import com.chatop.model.Authentication;
import com.chatop.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private AuthenticationService AuthenticationService;

    @Operation(summary = "auth register")
    @PostMapping("/register")
    public ResponseEntity<Authentication> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(AuthenticationService.register(request));
    }
}
