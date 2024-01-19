package com.chatop.controller;

import java.util.Collections;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.model.User;
import com.chatop.service.AuthenticationService;
import com.chatop.service.JWTService;
import com.chatop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;



@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private JWTService jwtService;
    private UserService userService;

    public AuthController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody LoginRequest loginDetails) {
        String email = loginDetails.getEmail();
        String password = loginDetails.getPassword();
    
        User user = userService.getUserByEmail(email);
    
        if (user == null || !userService.checkPassword(user, password)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found or incorrect credentials"));
        }
    
        Map<String, String> tokenObject = jwtService.generateToken(user);
        return ResponseEntity.ok(tokenObject);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest registrationDetails) {
        String email = registrationDetails.getEmail();
        String name = registrationDetails.getName();
        String password = registrationDetails.getPassword();
    
        boolean userExists = userService.isUserExistByEmail(email);
        if (userExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "User with this email already exists"));
        }
    
        try {
            User newUser = userService.createUser(email, name, password);
    
            Map<String, String> tokenObject = jwtService.generateToken(newUser);
    
            return ResponseEntity.ok(tokenObject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @Operation(security = @SecurityRequirement(name = "BearerAuth"), summary = "Get current user's information")
    @GetMapping("/me")
    public ResponseEntity<User> getUser(HttpServletRequest request) {

        final String authHeader = request.getHeader("Authorization");

        String jwt = authHeader.substring(7);
        String userEmail = jwtService.extractUserEmail(jwt);
        return ResponseEntity.ok(userService.getUserByEmail(userEmail));

    }
}