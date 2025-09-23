package br.edu.infnet.tenisdemesaapi.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import br.edu.infnet.tenisdemesaapi.dto.LoginDTO;
import br.edu.infnet.tenisdemesaapi.dto.RegisterDTO;
import br.edu.infnet.tenisdemesaapi.exception.InvalidLoginException;
import br.edu.infnet.tenisdemesaapi.exception.UserNotFoundException;
import br.edu.infnet.tenisdemesaapi.model.User;
import br.edu.infnet.tenisdemesaapi.repository.UserRepository;
import br.edu.infnet.tenisdemesaapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public User getById(Long id) {
		var user = userRepository.getById(id);
		
		return user;
	}
	@Override
	public User register(RegisterDTO model) {
		var user = User.NewUser(model.name, model.email, model.password);
		var result = userRepository.save(user);

		return result;
	}

	@Override
	public User login(LoginDTO model) {
		var user = userRepository.findByEmail(model.email);
		
		if(user == null) {
			throw new InvalidLoginException();
		}
		
		if(!model.password.equals(user.getPassword())) {
			throw new InvalidLoginException();
		}
		
		return user;
	}

	@Override
	public List<User> listUsersToInvite(Long userId) {
		var users = userRepository.listOtherUsers(userId);
		
		return users;
	}

	@Override
	public boolean updateUserPoints(Long userId, int points) {
		userRepository.updateRankingPoints(userId, points);
		
		return true;
	}

	@Override
	public User updateEmail(Long userId, String email) {
		var user = userRepository.getById(userId);
		
		if(user == null) {
			throw new UserNotFoundException();
		}
		
		user.setEmail(email);
		userRepository.save(user);
		return user;
	}
}
