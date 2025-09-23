package br.edu.infnet.tenisdemesaapi.exception;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends CustomException {

	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(message);
		status = HttpStatus.FORBIDDEN;
	}

	public ForbiddenException() {
		super("Usuário não possui permissão para realizar esta ação.");
		status = HttpStatus.FORBIDDEN;
	}
}
