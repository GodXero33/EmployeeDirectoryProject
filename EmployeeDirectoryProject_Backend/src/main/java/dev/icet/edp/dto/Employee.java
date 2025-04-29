package dev.icet.edp.dto;

import dev.icet.edp.util.enums.EmployeeDepartment;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
	private Long id;

	@Pattern(regexp = "^[A-Za-z ]{1,100}$", message = "Invalid username: Name must be 1–100 characters long and contain only letters and spaces.")
	private String name;

	@Email(message = "Invalid email address")
	private String email;

	private EmployeeDepartment department;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
