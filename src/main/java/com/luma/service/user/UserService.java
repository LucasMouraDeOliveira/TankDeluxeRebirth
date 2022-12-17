package com.luma.service.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.luma.dto.user.UserDTO;
import com.luma.exception.user.UsernameAlreadyUsedException;
import com.luma.model.User;
import com.luma.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private Optional<User> findByName(String name) {
		return this.userRepository.findByName(name);
	}
	
	private User save(User user) {
		return this.userRepository.save(user);
	}

	public User createUser(UserDTO userDTO) throws UsernameAlreadyUsedException {

		if (this.findByName(userDTO.getUsername()).isPresent()) {
			throw new UsernameAlreadyUsedException(userDTO.getUsername());
		}

		User user = new User();
		user.setPublicId(UUID.randomUUID());
		user.setName(userDTO.getUsername());
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

		return this.save(user);
	}

}
