package br.edu.infnet.tenisdemesaapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import java.net.URI;
import java.net.URISyntaxException;

import br.edu.infnet.tenisdemesaapi.dto.LoginDTO;
import br.edu.infnet.tenisdemesaapi.dto.LoginResponseDTO;
import br.edu.infnet.tenisdemesaapi.dto.RegisterDTO;
import br.edu.infnet.tenisdemesaapi.dto.UpdateEmailDTO;
import br.edu.infnet.tenisdemesaapi.dto.UserDTO;
import br.edu.infnet.tenisdemesaapi.exception.InternalServerErrorException;
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
	public ResponseEntity<UserDTO> register(@RequestBody RegisterDTO model) throws URISyntaxException{
		var user = userService.register(model);
		
		var url = getUserURI(user.getId());
		var response = new UserDTO(user.getId(), user.getName(), user.getEmail());
		return ResponseEntity.created(url).body(response);
	}
	
	private URI getUserURI(Long id) {
		try {
			return new URI("/api/user/"+id);
		}catch(URISyntaxException ex) {
			throw new InternalServerErrorException();
		}
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
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> user(@PathVariable long id){
		var user = userService.getById(id);
		
		var response = new UserDTO(user.getId(), user.getName(), user.getEmail());
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
