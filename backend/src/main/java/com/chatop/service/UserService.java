package com.chatop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.model.User;
import com.chatop.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

	private UserRepository userRepository;
	
	public Optional<User> getInfosUserById(Long id) {
		return userRepository.findById(id);
	}
}
