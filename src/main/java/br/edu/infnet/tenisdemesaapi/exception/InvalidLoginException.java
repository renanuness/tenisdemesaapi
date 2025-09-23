package br.edu.infnet.tenisdemesaapi.exception;

import org.springframework.http.HttpStatus;

public class InvalidLoginException extends CustomException{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public InvalidLoginException() {
			super("Usuário não encontrado");
			status = HttpStatus.BAD_REQUEST;
		}
}
