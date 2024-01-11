package com.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.model.Authentication;
import com.chatop.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService AuthenticationService;

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Enregister un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur enregistré"),
            @ApiResponse(responseCode = "400", description = "Utilisateur non enregistré")
    })
    public ResponseEntity<Authentication> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(AuthenticationService.register(request));
    }

    @PostMapping("/login")
    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur connecté"),
            @ApiResponse(responseCode = "400", description = "Utilisateur non connecté")
    })
    public ResponseEntity<Authentication> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(AuthenticationService.login(request));
    }
}
