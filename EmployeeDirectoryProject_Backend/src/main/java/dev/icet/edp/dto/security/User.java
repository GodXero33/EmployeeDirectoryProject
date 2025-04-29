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

	@NotBlank(message = "Username can't be empty")
	@Pattern(regexp = "^[A-Za-z ]{1,100}$", message = "Invalid username: Name must be 1–100 characters long and contain only letters and spaces.")
	private String username;

	private String password;

	@NotNull(message = "User role can't be null")
	private UserRole role;
}
