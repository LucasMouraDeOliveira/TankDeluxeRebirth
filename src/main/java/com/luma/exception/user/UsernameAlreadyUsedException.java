package com.luma.exception.user;

public class UsernameAlreadyUsedException extends Exception {

	private static final long serialVersionUID = 5103530680711825778L;

	public UsernameAlreadyUsedException(String username) {
		super(String.format("User name %s already taken", username));
	}

}