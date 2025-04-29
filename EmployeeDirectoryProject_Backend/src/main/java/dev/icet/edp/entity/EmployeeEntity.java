package dev.icet.edp.entity;

import dev.icet.edp.util.enums.EmployeeDepartment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
	private Long id;
	private String name;
	private String email;
	private EmployeeDepartment department;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
