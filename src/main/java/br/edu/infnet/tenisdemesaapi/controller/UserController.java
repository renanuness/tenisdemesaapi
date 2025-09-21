package br.edu.infnet.tenisdemesaapi.controller;

import java.net.http.HttpRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.infnet.tenisdemesaapi.dto.LoginDTO;
import br.edu.infnet.tenisdemesaapi.dto.LoginResponseDTO;
import br.edu.infnet.tenisdemesaapi.dto.RegisterDTO;
import br.edu.infnet.tenisdemesaapi.dto.UpdateEmailDTO;
import br.edu.infnet.tenisdemesaapi.dto.UserDTO;
import br.edu.infnet.tenisdemesaapi.model.User;
import br.edu.infnet.tenisdemesaapi.service.UserService;

@Controller
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterDTO model){
		var response = userService.register(model);
		
		return ResponseEntity.ok().body("Registrado com sucesso");
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO model){
		var user = userService.login(model);
		
		if(user == null) {
			return ResponseEntity.badRequest().body(LoginResponseDTO.fail());
		}
		var  response = LoginResponseDTO.success(user);
		return ResponseEntity.ok().body(response);
	}
	
	@GetMapping("/listUsersToInvite")
	public ResponseEntity<List<User>> listUsersToInvite(@RequestHeader Long userId){
		var result = userService.listUsersToInvite(userId);
		
		return ResponseEntity.ok().body(result);
	}
	
	@PutMapping("/updateEmail")
	public ResponseEntity<UserDTO> updateEmail(@RequestHeader Long userId, @RequestBody UpdateEmailDTO model){
		var user = userService.updateEmail(userId, model.email);
		
		var response = new UserDTO(user.getId(), user.getName(), user.getEmail());
		return ResponseEntity.ok().body(response);
	}

}
