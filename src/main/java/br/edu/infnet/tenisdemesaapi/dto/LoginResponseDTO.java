package br.edu.infnet.tenisdemesaapi.dto;

import br.edu.infnet.tenisdemesaapi.model.User;

public class LoginResponseDTO {
	public boolean success;
	public UserDTO user;
	
	private LoginResponseDTO() {
		this.success = false;
	}
	
	private LoginResponseDTO(User user) {
		this.success = true;
		this.user = new UserDTO(user.getId(),user.getName(), user.getEmail());
	}
	
	
	public static LoginResponseDTO success(User user) {
		return new LoginResponseDTO(user);
	}
	
	public static LoginResponseDTO fail() {
		return new LoginResponseDTO();
	}
}
