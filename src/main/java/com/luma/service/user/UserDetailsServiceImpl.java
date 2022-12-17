package com.luma.service.user;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.luma.model.User;
import com.luma.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByName(username).orElseThrow(() -> new UsernameNotFoundException(username));
		return this.toDetail(user);
	}

	private UserDetails toDetail(User user) {
		String username = user.getName();
		String password = user.getPassword();
		return new org.springframework.security.core.userdetails.User(username, password, Collections.emptyList());
	}

}