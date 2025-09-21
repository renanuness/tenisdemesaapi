package br.edu.infnet.tenisdemesaapi.service;

import java.util.List;

import br.edu.infnet.tenisdemesaapi.dto.LoginDTO;
import br.edu.infnet.tenisdemesaapi.dto.RegisterDTO;
import br.edu.infnet.tenisdemesaapi.model.User;

public interface UserService {
	User getById(Long id);
	User register(RegisterDTO model);
	User login(LoginDTO model);
	User updateEmail(Long userId, String email);
	List<User> listUsersToInvite(Long userId);
	boolean updateUserPoints(Long userId, int points);
}
