package dev.icet.edp.service.custom.security;

import dev.icet.edp.dto.security.TokenAndUser;
import dev.icet.edp.dto.security.User;
import dev.icet.edp.service.SuperService;
import dev.icet.edp.util.Response;

public interface UserService extends SuperService<User> {
	Response<TokenAndUser> verify (User user);
	Response<Boolean> isUsernameExist (String username);
	Response<Boolean> isEmployeeExistById (Long employeeId);
	Response<Boolean> isEmployeeAlreadyUser (Long employeeId);
}
