package br.edu.infnet.tenisdemesaapi.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends CustomException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException() {
		super("Usuário não encontrado");
		status = HttpStatus.NOT_FOUND;
	}
}
