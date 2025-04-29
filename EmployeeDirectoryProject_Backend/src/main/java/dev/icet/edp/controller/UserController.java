package dev.icet.edp.controller;

import dev.icet.edp.dto.security.TokenAndUser;
import dev.icet.edp.dto.security.User;
import dev.icet.edp.util.ControllerResponseUtil;
import dev.icet.edp.util.CustomHttpResponse;
import dev.icet.edp.service.custom.security.UserService;
import dev.icet.edp.util.Response;
import dev.icet.edp.util.enums.ResponseType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ControllerResponseUtil controllerResponseUtil;

	@PostMapping("/login")
	public CustomHttpResponse<TokenAndUser> login (@RequestBody User user) {
		final Response<TokenAndUser> response = this.userService.verify(user);

		return switch (response.getStatus()) {
			case SUCCESS -> new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "Authorized");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.UNAUTHORIZED, null, "Unauthorized");
		};
	}

	@PostMapping("/register")
	public CustomHttpResponse<User> register (@Valid @RequestBody User user, BindingResult result) {
		if (result.hasErrors()) return this.controllerResponseUtil.getInvalidDetailsResponse(result);
		if (user.getPassword() == null) return this.controllerResponseUtil.getInvalidDetailsResponse("Password is required");

		final Response<Boolean> usernameExistResponse = this.userService.isUsernameExist(user.getUsername());

		if (usernameExistResponse.getStatus() == ResponseType.SERVER_ERROR) return this.controllerResponseUtil.getServerErrorResponse();
		if (usernameExistResponse.getStatus() == ResponseType.FOUND) return new CustomHttpResponse<>(HttpStatus.CONFLICT, null, "Username is already taken");

		final Response<Boolean> employeeExistResponse = this.userService.isEmployeeExistById(user.getEmployeeId());

		if (employeeExistResponse.getStatus() == ResponseType.SERVER_ERROR) return this.controllerResponseUtil.getServerErrorResponse();
		if (employeeExistResponse.getStatus() == ResponseType.NOT_FOUND) return new CustomHttpResponse<>(HttpStatus.BAD_REQUEST, null, "No employee has found with given employeeId");

		final Response<Boolean> employeeAlreadyUserResponse = this.userService.isEmployeeAlreadyUser(user.getEmployeeId());

		if (employeeAlreadyUserResponse.getStatus() == ResponseType.SERVER_ERROR) return this.controllerResponseUtil.getServerErrorResponse();
		if (employeeAlreadyUserResponse.getStatus() == ResponseType.FOUND) return new CustomHttpResponse<>(HttpStatus.CONFLICT, null, "The target employee is already has user account");

		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		final Response<User> response = this.userService.add(user);

		if (response.getData() != null) response.getData().setPassword(null);

		return response.getStatus() == ResponseType.CREATED ?
			new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "User added") :
			this.controllerResponseUtil.getServerErrorResponse();
	}

	@PutMapping("/update")
	public CustomHttpResponse<User> update (@Valid @RequestBody User user, BindingResult result) {
		if (result.hasErrors()) return this.controllerResponseUtil.getInvalidDetailsResponse(result);
		if (user.getEmployeeId() == null) return this.controllerResponseUtil.getInvalidDetailsResponse("\"User employeeId required for update");

		final Response<Boolean> userExistResponse = this.userService.isUsernameExist(user.getUsername());

		if (userExistResponse.getStatus() == ResponseType.SERVER_ERROR) return this.controllerResponseUtil.getServerErrorResponse();
		if (userExistResponse.getStatus() == ResponseType.FOUND) return new CustomHttpResponse<>(HttpStatus.CONFLICT, null, "Username is already taken");

		if (user.getPassword() != null) user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		final Response<User> response = this.userService.update(user);

		if (response.getData() != null) response.getData().setPassword(null);

		return switch (response.getStatus()) {
			case UPDATED -> new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "User updated");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.NOT_MODIFIED, null, "User update failed");
		};
	}

	@DeleteMapping("/delete/{employeeId}")
	public CustomHttpResponse<Object> delete (@PathVariable("employeeId") Long employeeId) {
		if (employeeId <= 0) return new CustomHttpResponse<>(HttpStatus.BAD_REQUEST, null, "employeeId can't be zero or negative");

		final Response<Object> response = this.userService.delete(employeeId);

		return switch (response.getStatus()) {
			case DELETED -> new CustomHttpResponse<>(HttpStatus.OK, null, "User deleted");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.NOT_MODIFIED, null, "Failed to delete user");
		};
	}
}
