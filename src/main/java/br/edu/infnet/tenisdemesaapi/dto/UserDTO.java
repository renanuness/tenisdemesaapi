package br.edu.infnet.tenisdemesaapi.dto;

public class UserDTO {
	public long id;
	public String name;
	public String email;
	
	public UserDTO(Long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
}
