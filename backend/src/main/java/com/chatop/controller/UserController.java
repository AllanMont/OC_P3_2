package com.chatop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.model.User;
import com.chatop.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	
  	@Autowired
  	public UserController(UserService userService) {
      this.userService = userService;
  	}
  	
	@GetMapping("/{id}")
	public ResponseEntity<User> getInfosUserById(@PathVariable Long id) {
	    Optional<User> optionalUser = userService.getInfosUserById(id);

	    if (optionalUser.isPresent()) {
	        User foundUser = optionalUser.get();
	        return ResponseEntity.ok(foundUser);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
