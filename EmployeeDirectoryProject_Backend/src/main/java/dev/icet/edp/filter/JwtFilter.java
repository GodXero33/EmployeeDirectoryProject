package dev.icet.edp.filter;

import dev.icet.edp.service.custom.security.JWTService;
import dev.icet.edp.service.custom.security.CustomUserDetailsService;
import dev.icet.edp.util.enums.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JWTService jwtService;
	private final ApplicationContext context;
	private final Logger consoleLogger;

	@Override
	protected void doFilterInternal (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		UserRole role = null;

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);

			try {
				username = this.jwtService.extractUsername(token);
				role = this.jwtService.extractRole(token);
			} catch (Exception exception) {
				this.consoleLogger.warn("Invalid JWT token provided: {}", exception.getMessage());
			}
		}

		if (username != null && role != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			final UserDetails userDetails = this.context.getBean(CustomUserDetailsService.class).loadUserByUsername(username);

			if (userDetails == null) return;

			if (this.jwtService.validateToken(token, userDetails)) {
				final UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
					userDetails,
					null,
					List.of(new SimpleGrantedAuthority(role.name()))
				);

				userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}
