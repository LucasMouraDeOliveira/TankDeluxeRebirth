package com.luma.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luma.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	public Optional<User> findByPublicId(UUID publicId);
	
	public Optional<User> findByName(String name);

}