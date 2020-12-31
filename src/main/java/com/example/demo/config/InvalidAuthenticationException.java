package com.example.demo.config;

import org.springframework.security.core.AuthenticationException;

public class InvalidAuthenticationException extends AuthenticationException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -761503632186596342L;

	public InvalidAuthenticationException(String e) {
        super(e);
    }
}
