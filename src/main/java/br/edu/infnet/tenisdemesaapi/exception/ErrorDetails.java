package br.edu.infnet.tenisdemesaapi.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
	public LocalDateTime timestamp;
	public String message;
	public String description;
	
	public ErrorDetails(LocalDateTime timestamp, String message, String description) {
		this.timestamp = timestamp;
		this.message = message;
		this.description = description;
	}
	
}
