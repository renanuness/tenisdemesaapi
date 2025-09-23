package br.edu.infnet.tenisdemesaapi.exception;

import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException{
	public CustomException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
	public HttpStatus status;
}