package com.chatop.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.chatop.model.User;

@Service
public class JWTService {

    private JwtEncoder jwtEncoder;
    private JwtDecoder jwtDecoder;

    public JWTService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public Map<String, String> generateToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.DAYS))
                .subject(user.getName())
                .claim("email", user.getEmail())
                .build();
        
        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
        Jwt jwt = this.jwtEncoder.encode(encoderParameters);
        
        Map<String, String> tokenObject = new HashMap<>();
        tokenObject.put("token", jwt.getTokenValue());
        
        return tokenObject;
    }

    public String extractUserEmail(String token) {
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getClaims().get("email").toString();
    }
}