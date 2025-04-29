package dev.icet.edp.controller;

import dev.icet.edp.dto.Employee;
import dev.icet.edp.service.custom.EmployeeService;
import dev.icet.edp.service.custom.MailService;
import dev.icet.edp.util.ControllerResponseUtil;
import dev.icet.edp.util.CustomHttpResponse;
import dev.icet.edp.util.Response;
import dev.icet.edp.util.enums.ResponseType;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@PreAuthorize("hasAuthority('ADMIN')")
public class EmployeeController {
	private final EmployeeService employeeService;
	private final MailService mailService;
	private final ControllerResponseUtil controllerResponseUtil;

	@GetMapping("/{id}")
	public CustomHttpResponse<Employee> get (@PathVariable("id") Long id) {
		if (id <= 0) return this.controllerResponseUtil.getInvalidDetailsResponse("Id can't be negative");

		final Response<Employee> response = this.employeeService.get(id);

		return switch (response.getStatus()) {
			case FOUND -> new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "Employee found");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.NOT_FOUND, null, "Employee not found");
		};
	}

	@GetMapping("/all")
	public CustomHttpResponse<List<Employee>> getAll () {
		final Response<List<Employee>> response = this.employeeService.getAll();

		return response.getStatus() == ResponseType.FOUND ?
			new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "All employees found") :
			this.controllerResponseUtil.getServerErrorResponse();
	}

	@PostMapping()
	public CustomHttpResponse<Employee> add (@Valid @RequestBody Employee employee, BindingResult result) {
		if (result.hasErrors()) return this.controllerResponseUtil.getInvalidDetailsResponse(result);
		if (employee.getId() != null) return this.controllerResponseUtil.getInvalidDetailsResponse("New employee can't have a ID");

		final Response<Boolean> emailExistResponse = this.employeeService.isEmailExist(employee.getEmail(), null);

		if (emailExistResponse.getStatus() == ResponseType.SERVER_ERROR) return this.controllerResponseUtil.getServerErrorResponse();
		if (emailExistResponse.getStatus() == ResponseType.FOUND) return new CustomHttpResponse<>(HttpStatus.CONFLICT, null, "The given email address is already in the system. Can't use again.");

		final Response<Employee> response = this.employeeService.add(employee);

		if (response.getStatus() == ResponseType.CREATED) this.mailService.sendEmployeeAddMail(response.getData());

		return response.getStatus() == ResponseType.CREATED ?
			new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "Employee created") :
			this.controllerResponseUtil.getServerErrorResponse();
	}

	@PutMapping()
	public CustomHttpResponse<Employee> update (@Valid @RequestBody Employee employee, BindingResult result) {
		if (result.hasErrors()) return this.controllerResponseUtil.getInvalidDetailsResponse(result);
		if (employee.getId() == null || employee.getId() <= 0) return this.controllerResponseUtil.getInvalidDetailsResponse("Employee id can't be null and must be non zero positive integer");

		final Response<Boolean> emailExistResponse = this.employeeService.isEmailExist(employee.getEmail(), employee.getId());

		if (emailExistResponse.getStatus() == ResponseType.SERVER_ERROR) return this.controllerResponseUtil.getServerErrorResponse();
		if (emailExistResponse.getStatus() == ResponseType.FOUND) return new CustomHttpResponse<>(HttpStatus.CONFLICT, null, "The given email address is already in the system. Can't use again.");

		final Response<Employee> response = this.employeeService.update(employee);

		return switch (response.getStatus()) {
			case UPDATED -> new CustomHttpResponse<>(HttpStatus.OK, response.getData(), "Employee updated");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.NOT_MODIFIED, null, "Employee not updated");
		};
	}

	@DeleteMapping("/{id}")
	public CustomHttpResponse<Object> delete (@PathVariable Long id) {
		if (id <= 0) return this.controllerResponseUtil.getInvalidDetailsResponse("Id can't be negative");

		final Response<Object> response = this.employeeService.delete(id);

		return switch (response.getStatus()) {
			case DELETED -> new CustomHttpResponse<>(HttpStatus.OK, null, "Employee deleted");
			case SERVER_ERROR -> this.controllerResponseUtil.getServerErrorResponse();
			default -> new CustomHttpResponse<>(HttpStatus.NOT_MODIFIED, null, "Employee delete failed");
		};
	}
}
