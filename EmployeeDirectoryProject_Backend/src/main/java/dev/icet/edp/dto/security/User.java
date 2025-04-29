package dev.icet.edp.dto.security;

import dev.icet.edp.util.enums.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@NotNull(message = "Employee id is required")
	@Min(value = 1, message = "Employee id can't be zero or negative")
	private Long employeeId;

	@Pattern(regexp = "^[a-zA-Z0-9_]{3,15}$", message = "Invalid username: Username must be 3–15 characters long and can only contain letters, numbers, and underscores.")
	private String username;

	private String password;

	@NotNull(message = "User role can't be null")
	private UserRole role;
}
