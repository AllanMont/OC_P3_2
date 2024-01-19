package com.chatop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.chatop.model.User;
import com.chatop.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	public Optional<User> getInfosUserById(Integer id) {
		return userRepository.findById(id);
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public boolean checkPassword(User user, String password) {
		return user.getPassword().equals(password);
	}

	public boolean isUserExistByEmail(String email) {
		return userRepository.findByEmail(email) != null;
	}

	public User createUser(String name, String email, String password) {
		return userRepository.save(User.builder().name(name).email(email).password(password).build());
	}
}
