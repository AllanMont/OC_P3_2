package com.chatop.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.dto.UserDto;
import com.chatop.model.User;
import com.chatop.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
  	
	@GetMapping("/{id}")
	@Operation(summary = "Obtenir un utilisateur par ID", description = "Récupère un utilisateur en fonction de son ID.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Utilisateur trouvé"),
			@ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
	})
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer id) {
	    Optional<User> optionalUser = userService.getInfosUserById(id);

	    if (optionalUser.isPresent()) {
	        User foundUser = optionalUser.get();
			UserDto userDto = new UserDto(foundUser.getId(),foundUser.getName(),foundUser.getEmail(),foundUser.getCreated_at(),foundUser.getUpdated_at());
	        return ResponseEntity.ok(userDto);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
}
