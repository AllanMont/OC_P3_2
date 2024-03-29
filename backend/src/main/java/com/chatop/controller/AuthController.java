package com.chatop.controller;

import java.util.Collections;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.LoginRequest;
import com.chatop.dto.RegisterRequest;
import com.chatop.dto.UserDto;
import com.chatop.model.User;
import com.chatop.service.AuthenticationService;
import com.chatop.service.JWTService;
import com.chatop.service.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private JWTService jwtService;
    private UserService userService;
    private AuthenticationService authService;
    private ModelMapper modelMapper;

    public AuthController(JWTService jwtService, UserService userService, AuthenticationService authService, ModelMapper modelMapper) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.authService = authService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody LoginRequest loginDetails) {
        String email = loginDetails.getEmail();
        String password = loginDetails.getPassword();
    
        // Vérifier si l'utilisateur existe dans la base de données avec le login donné
        User user = userService.getUserByEmail(email);
    
        if (user == null || !userService.checkPassword(user, password)) {
            // Utilisateur non trouvé ou mot de passe incorrect
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found or incorrect credentials"));
        }
    
        // Utilisateur trouvé et mot de passe correspondant
        Map<String, String> tokenObject = jwtService.generateToken(user);
        return ResponseEntity.ok(tokenObject);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterRequest registrationDetails) {
        String email = registrationDetails.getEmail();
        String name = registrationDetails.getName();
        String password = registrationDetails.getPassword();
    
        // Vérifier si l'utilisateur existe déjà avec cet email en utilisant le service
        boolean userExists = userService.isUserExistByEmail(email);
        if (userExists) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", "User with this email already exists"));
        }
    
        try {
            // Utiliser le service pour créer un nouvel utilisateur
            User newUser = userService.createUser(email, name, password);
    
            // Générer un token pour le nouvel utilisateur enregistré
            Map<String, String> tokenObject = jwtService.generateToken(newUser);
    
            return ResponseEntity.ok(tokenObject);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getLoggedInUserInfo(Authentication authentication) {
        String mailToVerify = authentication.getName();

        User user = authService.getLoggedInUser(mailToVerify);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        
        UserDto userDTO = modelMapper.map(user, UserDto.class);
        return ResponseEntity.ok(userDTO);
    }
}