package com.luma.dto.user;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
	
	@JsonInclude(Include.NON_NULL)
	private UUID id;

	private String username;
	
	@JsonInclude(Include.NON_NULL)
	private String password;
	
}