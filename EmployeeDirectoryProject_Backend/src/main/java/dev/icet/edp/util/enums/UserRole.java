package dev.icet.edp.util.enums;

import java.util.Arrays;

public enum UserRole {
	ADMIN;

	public static UserRole fromName (String name) {
		return name == null ? null : Arrays.stream(UserRole.values()).
			filter(userRole -> userRole.name().equalsIgnoreCase(name)).
			findFirst().
			orElse(null);
	}
}
