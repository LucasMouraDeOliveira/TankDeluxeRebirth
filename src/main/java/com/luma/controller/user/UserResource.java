package com.luma.controller.user;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luma.dto.user.UserDTO;
import com.luma.exception.user.UsernameAlreadyUsedException;
import com.luma.model.User;
import com.luma.service.user.UserService;

@RestController
@RequestMapping("${apiPrefix}/users")
public class UserResource {
	
	private static final Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<UUID> createUser(@RequestBody UserDTO userDTO) {
		try {
			User user = this.userService.createUser(userDTO);
			return ResponseEntity.ok(user.getPublicId());
		} catch(UsernameAlreadyUsedException e) {
			logger.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}
