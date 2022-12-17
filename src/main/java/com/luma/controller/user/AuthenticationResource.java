package com.luma.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luma.configuration.JwtTokenUtils;
import com.luma.dto.user.JwtRequest;
import com.luma.dto.user.JwtToken;

@RestController
@RequestMapping("${apiPrefix}/auth")
public class AuthenticationResource {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtTokenUtils tokenUtils;
	
	@PostMapping
	public ResponseEntity<JwtToken> authenticate(@RequestBody JwtRequest jwtRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
		Authentication auth = authManager.authenticate(authToken);
		String token = tokenUtils.generateJwtToken(auth);
		return ResponseEntity.ok(new JwtToken(auth.getName(), token));
	}

}