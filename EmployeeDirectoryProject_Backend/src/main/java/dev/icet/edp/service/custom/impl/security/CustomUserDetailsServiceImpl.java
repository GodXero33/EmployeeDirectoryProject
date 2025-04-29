package dev.icet.edp.service.custom.impl.security;

import dev.icet.edp.dto.security.User;
import dev.icet.edp.dto.security.CustomUserDetails;
import dev.icet.edp.entity.security.UserEntity;
import dev.icet.edp.repository.custom.security.UserRepository;
import dev.icet.edp.service.custom.security.CustomUserDetailsService;
import dev.icet.edp.util.Response;
import dev.icet.edp.util.enums.ResponseType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	private final UserRepository userRepository;
	private final ModelMapper mapper;
	private final Logger logger;

	@Override
	public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
		final Response<UserEntity> response = this.userRepository.getByUserName(username);

		if (response.getStatus() == ResponseType.SERVER_ERROR) return null;

		if (response.getStatus() == ResponseType.NOT_FOUND) {
			this.logger.warn("Requested user not found: {}", username);
			return null;
		}

		return new CustomUserDetails(this.mapper.map(response.getData(), User.class));
	}
}
