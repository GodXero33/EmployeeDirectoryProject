package dev.icet.edp.service.custom.security;

import dev.icet.edp.util.enums.UserRole;
import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {
	String generateToken (String adminName, UserRole role);
	String extractUsername (String token);
	UserRole extractRole (String token);
	boolean validateToken (String token, UserDetails userDetails);
}
