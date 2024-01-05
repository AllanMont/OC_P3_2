package com.chatop.service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chatop.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
    private String secretKey;

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
      }
      
        public Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        public Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }
        
        public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }
        
        public String generateToken(User user) {
          return Jwts.builder()
              .setSubject(user.getEmail())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
              .signWith(SignatureAlgorithm.HS256, secretKey)
              .compact();
          }
          
      public String generateToken(Map<String, Objects> extractClaims, User user) {
        return Jwts.builder()
            .setClaims(extractClaims)
            .setSubject(user.getEmail())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
      }


        public Boolean validateToken(String token, UserDetails userDetails) {
            final String username = extractUserEmail(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        }
}
