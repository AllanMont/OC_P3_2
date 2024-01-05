package com.chatop.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.chatop.service.JwtService;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                  @Nonnull HttpServletResponse response,
                                  @Nonnull FilterChain filterChain)
      throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");
  
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      String jwt = authorizationHeader.substring(7);
  
      if (jwt != null && !jwt.isEmpty() && jwt.contains(".")) {
        String userEmail = jwtService.extractUserEmail(jwt);
  
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
  
          if (jwtService.isTokenValid(jwt, userDetails)) {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            usernamePasswordAuthenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext()
                .setAuthentication(usernamePasswordAuthenticationToken);
          }
        }
      }
    }
  
    filterChain.doFilter(request, response);
  }
  

}