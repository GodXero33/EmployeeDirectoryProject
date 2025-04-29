package dev.icet.edp.entity.security;

import dev.icet.edp.util.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
	private Long employeeId;
	private String username;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastLogin;
	private UserRole role;
}
