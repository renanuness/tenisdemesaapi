package br.edu.infnet.tenisdemesaapi.exception;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends CustomException {

	private static final long serialVersionUID = 1L;

	public InternalServerErrorException() {
		super("Usuário não encontrado");
		status = HttpStatus.INTERNAL_SERVER_ERROR;
	}
}
