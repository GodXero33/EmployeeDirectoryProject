package dev.icet.edp.util.enums;

import java.util.Arrays;

public enum EmployeeDepartment {
	HR, IT, FINANCE, OPERATIONS;

	public static EmployeeDepartment fromName (String name) {
		return name == null ? null : Arrays.stream(EmployeeDepartment.values()).
			filter(department -> department.name().equalsIgnoreCase(name)).
			findFirst().
			orElse(null);
	}
}
