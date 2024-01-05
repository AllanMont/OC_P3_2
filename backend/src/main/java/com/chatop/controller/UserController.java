package com.chatop.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.User;
import com.chatop.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
  	
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable Long id) {
	    Optional<User> optionalUser = userService.getInfosUserById(id);

	    if (optionalUser.isPresent()) {
	        Object foundUser = optionalUser.get();
	        return ResponseEntity.ok(foundUser);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
