package dev.icet.edp.service.custom.impl.security;

import dev.icet.edp.dto.security.TokenAndUser;
import dev.icet.edp.dto.security.User;
import dev.icet.edp.entity.security.UserEntity;
import dev.icet.edp.repository.custom.security.UserRepository;
import dev.icet.edp.service.SuperServiceHandler;
import dev.icet.edp.service.custom.security.UserService;
import dev.icet.edp.service.custom.security.JWTService;
import dev.icet.edp.util.Response;
import dev.icet.edp.util.enums.ResponseType;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	private final SuperServiceHandler<User, UserEntity> serviceHandler;
	private final ModelMapper mapper;

	public UserServiceImpl (UserRepository userRepository, ModelMapper mapper, AuthenticationManager authenticationManager, JWTService jwtService) {
		this.userRepository = userRepository;
		this.mapper = mapper;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.serviceHandler = new SuperServiceHandler<>(userRepository, mapper, User.class, UserEntity.class);
	}

	@Override
	public Response<User> get (Long id) {
		return this.serviceHandler.get(id);
	}

	@Override
	public Response<List<User>> getAll () {
		return this.serviceHandler.getAll();
	}

	@Override
	public Response<User> add (User user) {
		return this.serviceHandler.add(user);
	}

	@Override
	public Response<User> update (User user) {
		return this.serviceHandler.update(user);
	}

	@Override
	public Response<Object> delete (Long id) {
		return this.serviceHandler.delete(id);
	}

	@Override
	public Response<TokenAndUser> verify (User user) {
		final Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		final Response<UserEntity> response = this.userRepository.getByUserNameForAuth(user.getUsername());

		if (response.getStatus() == ResponseType.NOT_FOUND) return new Response<>(null, ResponseType.FAILED);
		if (response.getStatus() == ResponseType.SERVER_ERROR) return new Response<>(null, ResponseType.SERVER_ERROR);

		return authentication.isAuthenticated() ?
			new Response<>(TokenAndUser.builder()
				.user(this.mapper.map(response.getData(), User.class))
				.token(this.jwtService.generateToken(user.getUsername(), response.getData().getRole()))
				.build(), ResponseType.SUCCESS) :
			new Response<>(null, ResponseType.FAILED);
	}

	@Override
	public Response<Boolean> isUsernameExist (String username) {
		return this.userRepository.isUsernameExist(username);
	}

	@Override
	public Response<Boolean> isEmployeeExistById (Long employeeId) {
		return this.userRepository.isEmployeeExistById(employeeId);
	}

	@Override
	public Response<Boolean> isEmployeeAlreadyUser (Long employeeId) {
		return this.userRepository.isEmployeeAlreadyUser(employeeId);
	}
}
